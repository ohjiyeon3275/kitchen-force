package com.kitchenforce.domain.orders.dto


data class OrderTableDto (
    internal val userId: Long,
    internal val emptyness: Boolean,
    internal val tableName: String,
    internal val numberOfGuests: Int,

    internal val orderList: List<OrderDto>
        ){
}