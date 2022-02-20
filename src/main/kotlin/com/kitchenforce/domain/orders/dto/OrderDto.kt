package com.kitchenforce.domain.orders.dto

import com.kitchenforce.domain.enum.OrderStatus
import com.kitchenforce.domain.enum.OrderType

data class OrderDto(
    val userId: Long,
    val orderType: OrderType,
    val orderStatus: OrderStatus,
    val paymentMethod: String,
    val requirement: String,
    val deliveryAddress: String,
    val orderTableDto: OrderTableDto?,
    val orderMenuDtoList: List<OrderMenuDto>
)
