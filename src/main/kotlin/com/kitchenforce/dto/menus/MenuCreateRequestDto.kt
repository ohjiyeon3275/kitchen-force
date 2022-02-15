package com.kitchenforce.dto.menus

data class MenuCreateRequestDto(
    val name: String,
    val products: List<Int>,
    val price: Int
)