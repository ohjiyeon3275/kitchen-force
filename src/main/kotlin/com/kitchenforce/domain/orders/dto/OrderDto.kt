package com.kitchenforce.domain.orders.dto

import com.kitchenforce.domain.enum.OrderStatus
import com.kitchenforce.domain.enum.OrderType

data class OrderDto(
    val orderType: OrderType,
    val paymentMethod: String,
    val requirement: String,
    val deliveryAddress: String,
    val orderStatus: OrderStatus,
    val orderMenuDtoList: MutableList<OrderMenuDto>
)
