package com.kitchenforce.domain.enums

enum class OrderStatus(
    val title: String
) {
    WAITING("주문 대기"),
    ACCEPTED("주문 접수"),
    SERVED("주문 서빙"),
    CLOSED("주문 종료"),
}
