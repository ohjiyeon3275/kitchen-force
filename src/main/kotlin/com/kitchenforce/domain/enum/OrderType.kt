package com.kitchenforce.domain.enum

enum class OrderType(
    val title: String
) {
    DELIVERY("배달"),
    TAKEOUT("포장"),
    EATIN("매장"),
}
