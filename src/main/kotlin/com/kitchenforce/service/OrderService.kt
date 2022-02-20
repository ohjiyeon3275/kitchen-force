package com.kitchenforce.service

import com.kitchenforce.domain.delivery.DeliveryAddressRepository
import com.kitchenforce.domain.enum.OrderStatus
import com.kitchenforce.domain.enum.OrderType
import com.kitchenforce.domain.menus.MenuRepository
import com.kitchenforce.domain.orders.*
import com.kitchenforce.domain.orders.dto.OrderDto
import com.kitchenforce.domain.orders.dto.OrderMenuDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class OrderService (
    private val orderRepository: OrderRepository,
    private val orderTableRepository: OrderTableRepository,
    private val orderMenuRepository: OrderMenuRepository,
    private val menuRepository: MenuRepository,
    private val deliveryAddressRepository: DeliveryAddressRepository
        ){


    @Transactional
    fun create(dto: OrderDto) {

        val order: Order = Order(
            userId = dto.userId,
            orderStatus = dto.orderStatus,
            orderType = dto.orderType,
            paymentMethod = dto.paymentMethod,
            paymentPrice = 0,
            requirement = dto.requirement,
            deliveryAddress = deliveryAddressRepository.findByAddress(dto.deliveryAddress)
        )
        orderRepository.save(order)

        //비교안되면 OrderType.EATIN으로,
        if(dto.orderType == OrderType.EATIN) {
            val orderTable: OrderTable = OrderTable(
                name = dto.orderTableDto?.tableName ?: "테이블 지정 안됨.",
                emptyness = dto.orderTableDto?.emptyness ?: false,
                numberOfGuests = dto.orderTableDto?.numberOfGuests ?: 0,
                order = order
            )
            orderTableRepository.save(orderTable)
        }

        val orderMenuList: MutableList<OrderMenu> = ArrayList()
        for (orderMenuDto in dto.orderMenuDtoList){
            val orderMenu: OrderMenu = OrderMenu(
                quantity = orderMenuDto.quantity,
                order = order,
                menu = menuRepository.findByName(orderMenuDto.menuName)
            )
            orderMenuList.add(orderMenu)
        }
        orderMenuRepository.saveAll(orderMenuList)
    }

    fun get(): List<OrderDto> {

        val orderList: List<Order> = orderRepository.findAll()

        val orderDtoList: MutableList<OrderDto> = ArrayList()

        for(order in orderList){

            val orderDto: OrderDto = OrderDto(
                userId = order.userId,
                orderType = order.orderType,
                orderStatus = order.orderStatus,
                paymentMethod = order.paymentMethod,
                requirement = order.requirement,
                deliveryAddress = order.deliveryAddress?.address ?:"등록된 주소 없음.",
                orderTableDto = null,
                orderMenuDtoList = ArrayList()
            )
            orderDtoList.add(orderDto)
        }
        return orderDtoList
    }

    fun getOrder(userId: Long): OrderDto {

        val orderDtoNothing: OrderDto = OrderDto(
            userId = userId,
            orderType = OrderType.ERROR,
            orderStatus = OrderStatus.ERROR,
            paymentMethod = "",
            requirement = "",
            deliveryAddress = "",
            orderTableDto = null,
            orderMenuDtoList = ArrayList()
        )

        val order: Order? = orderRepository.findByUserId(userId)

        order?.let {

            val orderMenuDtoList: MutableList<OrderMenuDto> = ArrayList()
            for (orderMenu in order.orderMenuList) {

                val orderMenuDto: OrderMenuDto = OrderMenuDto(
                    quantity = orderMenu.quantity,
                    menuName = orderMenu.menu?.name ?: "메뉴 네임 미정"
                )
                orderMenuDtoList.add(orderMenuDto)
            }

            return OrderDto(
                userId = userId,
                orderType = order.orderType,
                orderStatus = order.orderStatus,
                paymentMethod = order.paymentMethod,
                requirement = order.requirement,
                deliveryAddress = order.deliveryAddress?.address ?: "주소 없음.",
                orderTableDto = null,
                orderMenuDtoList = order.orderMenuList.map {
                    OrderMenuDto(
                        quantity = it.quantity,
                        menuName = it.menu?.name ?: "메뉴 네임 미정"
                    )
                }
            )
        }?: return orderDtoNothing

    }



}