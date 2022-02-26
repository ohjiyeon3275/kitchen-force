package com.kitchenforce.dto.menus

import com.sun.istack.NotNull

data class MenuCreateRequestDto(
    val name: String,
    @NotNull
    val products: Map<Long, Int>,
    val price: Int
)