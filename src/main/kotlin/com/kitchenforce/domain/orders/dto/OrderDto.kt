package com.kitchenforce.domain.orders.dto

import com.kitchenforce.domain.delivery.Delivery
import com.kitchenforce.domain.enums.OrderStatus
import com.kitchenforce.domain.enums.OrderType
import javax.validation.Valid

data class OrderDto(
    val orderType: OrderType,
    val orderStatus: OrderStatus?,
    val paymentMethod: String,
    val requirement: String,
    val delivery: Delivery,
    val price: Long?,
    @Valid
    val orderTableDto: OrderTableDto?,
    @Valid
    val orderMenuDtoList: List<OrderMenuDto>
)
