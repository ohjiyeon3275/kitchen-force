package com.kitchenforce.domain.orders.dto

data class OrderDto (
    internal val orderType: String,
    internal val paymentMethod: String,
    internal val requirement: String,
    internal val deliveryAddress: String,
    internal val orderMenuList: List<OrderMenuDto>
        ){


}