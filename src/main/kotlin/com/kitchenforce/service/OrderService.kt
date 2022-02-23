package com.kitchenforce.service

import com.kitchenforce.common.exception.NotFoundException
import com.kitchenforce.domain.delivery.DeliveryAddressRepository
import com.kitchenforce.domain.enum.OrderType
import com.kitchenforce.domain.menus.MenuRepository
import com.kitchenforce.domain.orders.*
import com.kitchenforce.domain.orders.dto.OrderDto
import com.kitchenforce.domain.orders.dto.OrderMenuDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderTableRepository: OrderTableRepository,
    private val orderMenuRepository: OrderMenuRepository,
    private val menuRepository: MenuRepository,
    private val deliveryAddressRepository: DeliveryAddressRepository
) {
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

        if (dto.orderType == OrderType.EATIN) {
            val orderTable: OrderTable = OrderTable(
                name = dto.orderTableDto?.tableName ?: "테이블 지정 안됨.",
                emptyness = dto.orderTableDto?.emptyness ?: false,
                numberOfGuests = dto.orderTableDto?.numberOfGuests ?: 0,
                order = order
            )
            orderTableRepository.save(orderTable)
        }

        dto.orderMenuDtoList.map {
            OrderMenu(
                quantity = it.quantity,
                order = order,
                menu = menuRepository.findByName(it.menuName)
            )
        }.also { orderMenuRepository.saveAll(it) }
    }

    fun get(): List<OrderDto> {

        val orderList: List<Order> = orderRepository.findAll()

        orderList.map {
            OrderDto(
                userId = it.userId,
                orderType = it.orderType,
                orderStatus = it.orderStatus,
                paymentMethod = it.paymentMethod,
                requirement = it.requirement,
                deliveryAddress = it.deliveryAddress?.address ?: "등록된 주소 없음.",
                orderTableDto = null,
                orderMenuDtoList = ArrayList()
            )
        }.also { return it }
    }

    fun getOrder(userId: Long): OrderDto {

        val order: Order? = orderRepository.findByUserId(userId)

        order?.let {
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
        } ?: throw NotFoundException("접수된 주문이 존재하지 않습니다.")
    }
}
