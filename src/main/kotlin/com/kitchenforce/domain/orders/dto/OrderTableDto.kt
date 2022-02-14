package com.kitchenforce.domain.orders.dto

data class OrderTableDto(
    val userId: Long,
    val emptyness: Boolean,
    val tableName: String,
    val numberOfGuests: Int,
    val orderDtoList: MutableList<OrderDto> = ArrayList()
)
