package com.kitchenforce.domain.orders.dto

import javax.validation.constraints.Min

data class OrderMenuDto(
    @Min(0)
    val quantity: Long,
    val menuName: String
)
