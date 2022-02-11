package com.kitchenforce.domain.orders.dto

import com.kitchenforce.domain.enum.OrderStatus
import com.kitchenforce.domain.enum.OrderType

data class OrderDto(
    var orderType: OrderType,
    var paymentMethod: String="",
    var requirement: String="",
    var deliveryAddress: String="",
    var orderStatus: OrderStatus,
    var orderMenuDtoList: MutableList<OrderMenuDto> = ArrayList()
)
