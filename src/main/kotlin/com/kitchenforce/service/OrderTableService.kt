package com.kitchenforce.service

import com.kitchenforce.domain.delivery.DeliveryAddressRepository
import com.kitchenforce.domain.menus.Menu
import com.kitchenforce.domain.menus.MenuRepository
import com.kitchenforce.domain.orders.Order
import com.kitchenforce.domain.orders.OrderMenu
import com.kitchenforce.domain.orders.OrderMenuRepository
import com.kitchenforce.domain.orders.OrderRepository
import com.kitchenforce.domain.orders.OrderTable
import com.kitchenforce.domain.orders.OrderTableRepository
import com.kitchenforce.domain.orders.dto.OrderDto
import com.kitchenforce.domain.orders.dto.OrderMenuDto
import com.kitchenforce.domain.orders.dto.OrderTableDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderTableService(
    private val orderTableRepository: OrderTableRepository,
    private val orderRepository: OrderRepository,
    private val orderMenuRepository: OrderMenuRepository,
    private val menuRepository: MenuRepository,
    private val deliveryAddressRepository: DeliveryAddressRepository
) {

    @Transactional
    fun create(dto: OrderTableDto) {
        val orderTable: OrderTable = OrderTable(
            userId = dto.userId,
            name = dto.tableName,
            emptyness = dto.emptyness,
            numberOfGuests = dto.numberOfGuests
        )

        val savedOrder = orderTableRepository.save(orderTable)

        for (orderDto in dto.orderDtoList) {

            val order: Order = Order(
                orderType = orderDto.orderType,
                paymentPrice = 0,
                paymentMethod = orderDto.paymentMethod,
                requirement = orderDto.requirement,
                orderStatus = orderDto.orderStatus,
                orderTable = orderTable,
                deliveryAddress = deliveryAddressRepository.findByAddress(orderDto.deliveryAddress)!!
            )
            orderRepository.save(order)

            val orderMenus: MutableList<OrderMenu> = ArrayList()
            for (orderMenuDto in orderDto.orderMenuDtoList) {

                val menu: Menu = menuRepository.findByName(orderMenuDto.menuName)
                val orderMenu: OrderMenu = OrderMenu(
                    quantity = orderMenuDto.quantity,
                    order = order,
                    menu = menu
                )
                orderMenus.add(orderMenu)
            }
            orderMenuRepository.saveAll(orderMenus)
        }
    }

    fun get(): List<OrderTableDto> {

        val orderTableList: List<OrderTable>? = orderTableRepository.findAll()

        val orderTableDtoListNothing: MutableList<OrderTableDto> = ArrayList()
        val orderTableDtoList: MutableList<OrderTableDto> = ArrayList()

        orderTableList?.let{

            for(orderTable in orderTableList){
                val orderTableDto: OrderTableDto = OrderTableDto(
                    userId = orderTable.userId,
                    emptyness = orderTable.emptyness,
                    tableName = orderTable.name,
                    numberOfGuests = orderTable.numberOfGuests
                )
                orderTableDtoList.add(orderTableDto)
            }
            return orderTableDtoList
        }?: return orderTableDtoListNothing
    }

    fun orderInfo(userId: Long): OrderTableDto {

        val orderTable: OrderTable? = orderTableRepository.findByUserId(userId)

        val orderTableDtoNothing: OrderTableDto = OrderTableDto(
            userId = -1,
            emptyness = true,
            tableName = "-1",
            numberOfGuests = -1
        )

        orderTable?.let {
            val orderTableDto: OrderTableDto = OrderTableDto(
                userId = orderTable.userId,
                emptyness = orderTable.emptyness,
                tableName = orderTable.name,
                numberOfGuests = orderTable.numberOfGuests
            )
            val orderList: List<Order>? = orderTable.orderList

            if (orderList != null) {
                for (order in orderList) {

                    val orderDto: OrderDto = OrderDto(
                        orderType = order.orderType,
                        paymentMethod = order.paymentMethod,
                        requirement = order.requirement,
                        deliveryAddress = order.deliveryAddress.address,
                        orderStatus = order.orderStatus
                    )
                    val orderMenuList: List<OrderMenu> = order.orderMenuList

                    for (orderMenu in orderMenuList) {

                        val orderMenuDto: OrderMenuDto = OrderMenuDto(
                            quantity = orderMenu.quantity,
                            menuName = orderMenu.menu?.name ?: "메뉴 네임 미정"
                        )
                        orderDto.orderMenuDtoList.add(orderMenuDto)
                    }
                    orderTableDto.orderDtoList.add(orderDto)
                }
            }
            return orderTableDto
        } ?: return orderTableDtoNothing
    }
}
