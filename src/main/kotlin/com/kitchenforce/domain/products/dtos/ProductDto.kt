package com.kitchenforce.domain.products.dtos

import javax.validation.constraints.PositiveOrZero

data class ProductDto(
    val id: Long? = null,
    val name: String,
    @field:PositiveOrZero(message = "상품의 가격은 0보다 작을 수 없습니다.")
    val price: Int,
)
