package com.kitchenforce.domain.enum

enum class OrderStatus(
    val title: String
) {
    WAITING("주문 대기"),
    ACCEPTED("주문 접수"),
    CLOSE("주문 종료"),
}
