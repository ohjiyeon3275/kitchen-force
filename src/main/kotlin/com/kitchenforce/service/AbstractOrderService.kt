package com.kitchenforce.service

import com.kitchenforce.domain.orders.OrderRepository
import com.kitchenforce.domain.orders.dto.OrderDto

abstract class AbstractOrderService(
    open val orderRepository: OrderRepository
) {
    abstract fun createOrder(orderDto: OrderDto): OrderDto
    abstract fun updateOrder(orderId: Long, orderDto: OrderDto?): OrderDto
    fun getAllOrders(): List<OrderDto> {
        return orderRepository.findAll().map {
            it.toOrderDto()
        }
    }

    fun getOrderById(orderId: Long): OrderDto? {
        return orderRepository.getById(orderId).toOrderDto()
    }
}
