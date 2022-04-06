package com.kitchenforce.service

import com.kitchenforce.domain.orders.OrderRepository
import com.kitchenforce.domain.orders.dto.OrderDto
import org.springframework.stereotype.Service

@Service
class DeliveryOrderService(
    override val orderRepository: OrderRepository
) : AbstractOrderService(orderRepository) {
    override fun createOrder(orderDto: OrderDto): OrderDto {
        TODO("Not yet implemented")
    }

    override fun updateOrder(orderId: Long, orderDto: OrderDto?): OrderDto {
        TODO("Not yet implemented")
    }
}
