package com.kitchenforce.dto.menus

import com.sun.istack.NotNull

data class MenuCreateRequestDto(
    private val name: String,
    @NotNull
    private val products: List<MenuProductRequestDto>,
    private val price: Int
)