package com.kitchenforce.domain.orders.dto

data class OrderTableDto(
    val tableName: String,
    val emptyness: Boolean,
    val numberOfGuests: Int
)
