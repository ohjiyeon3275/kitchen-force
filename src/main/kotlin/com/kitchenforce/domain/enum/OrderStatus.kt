package com.kitchenforce.domain.enum

enum class OrderStatus(
    val id: Int,
    val title: String,
    val description: String
){
    REGISTERED(0, "주문 대기", "주문 대기 상태"),
    UNREGISTERED(1, "주문 접수", "주문 접수 상태"),
    WAITING(2, "주문 종료", "주문 종료 상태")
}