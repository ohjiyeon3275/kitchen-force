package com.kitchenforce.service

import com.kitchenforce.common.exception.NotFoundException
import com.kitchenforce.domain.enum.OrderStatus
import com.kitchenforce.domain.menus.MenuRepository
import com.kitchenforce.domain.orders.Order
import com.kitchenforce.domain.orders.OrderMenu
import com.kitchenforce.domain.orders.OrderMenuRepository
import com.kitchenforce.domain.orders.OrderRepository
import com.kitchenforce.domain.orders.OrderTable
import com.kitchenforce.domain.orders.OrderTableRepository
import com.kitchenforce.domain.orders.dto.OrderDto
import com.kitchenforce.domain.orders.exception.OrderErrorCodeType
import com.kitchenforce.domain.orders.exception.OrderException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TableOrderService(
    override val orderRepository: OrderRepository,
    val orderTableRepository: OrderTableRepository,
    val menuRepository: MenuRepository,
    val orderMenuRepository: OrderMenuRepository,
) : AbstractOrderService(orderRepository) {
    override fun createOrder(orderDto: OrderDto): OrderDto {

        return runCatching {
            // 신규주문 생성
            val newOrder = orderRepository.save(
                Order.generateTableOrder(
                    orderDto.orderType,
                    orderDto.paymentMethod,
                    orderDto.price,
                    orderDto.requirement
                )
            )

            val orderTable = OrderTable(
                name = orderDto.orderTableDto?.tableName ?: "테이블 지정 안됨.",
                emptiness = orderDto.orderTableDto?.emptiness ?: true,
                numberOfGuests = orderDto.orderTableDto?.numberOfGuests ?: 0,
                order = newOrder
            )

            val orderMenuList = orderDto.orderMenuDtoList.map {
                OrderMenu(
                    quantity = it.quantity,
                    order = newOrder,
                    menu = menuRepository.findByNameAndHidden(it.menuName, false) ?: throw NotFoundException("메뉴가 존재하지 않습니다.")
                )
            }

            orderTableRepository.save(orderTable)
            orderMenuRepository.saveAll(orderMenuList)
            newOrder.toOrderDto()
        }
            .getOrThrow()
    }

    override fun updateOrder(orderId: Long, orderDto: OrderDto?): OrderDto {
        return run {
            val order = orderRepository.findByIdOrNull(orderId) ?: throw OrderException.of(
                OrderErrorCodeType.ORDER_NOT_FOUND
            )
            val orderTable = orderTableRepository.findByOrderAndEmptiness(order, false)
                ?: throw OrderException.of(OrderErrorCodeType.SEARCH_TABLE_ORDER_FAILED)

            when (order.orderStatus) {
                OrderStatus.WAITING -> order.orderStatus = OrderStatus.ACCEPTED
                OrderStatus.ACCEPTED -> order.orderStatus = OrderStatus.SERVED
                OrderStatus.SERVED -> {
                    // TODO : 이미 Serving 된 상태에서 주문 메뉴를 추가 또는 취소 요청이 올 수도 있지 않을까?
                    order.orderStatus = OrderStatus.CLOSED
                    orderTable?.emptiness = true
                }
                else -> throw OrderException.of(OrderErrorCodeType.INVALID_ORDER_STATUS, errorMessage = "current Order Status : ${order.orderStatus}")
            }

            orderTableRepository.save(orderTable)
            orderRepository.save(order).toOrderDto()
        }
    }
}
