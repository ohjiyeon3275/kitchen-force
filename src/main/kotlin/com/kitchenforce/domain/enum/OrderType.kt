package com.kitchenforce.domain.enum

enum class OrderType (
    val id: Int,
    val title: String,
    val description: String
    ){
        DELIVERY(0, "배달", "배달 주문"),
        TAKEOUT(1, "포장", "포장 주문"),
        EATIN(2, "매장 취식", "매장 취식 주문")
    }