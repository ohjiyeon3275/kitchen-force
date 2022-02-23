package com.kitchenforce.domain.enum

enum class OrderStatus(
    val id: Int,
    val title: String,
    val description: String
) {
    WAITING(0, "주문 대기", "주문 대기 상태"),
    ACCEPTED(1, "주문 접수", "주문 접수 상태"),
    CLOSE(2, "주문 종료", "주문 종료 상태"),
}
