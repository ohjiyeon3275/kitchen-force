package com.kitchenforce.domain.orders.dto

import javax.validation.constraints.Min

data class OrderTableDto(
    val tableName: String,
    val emptiness: Boolean,
    @Min(0)
    val numberOfGuests: Int
)
