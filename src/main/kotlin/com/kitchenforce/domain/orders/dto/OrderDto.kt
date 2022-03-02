package com.kitchenforce.domain.orders.dto

import com.kitchenforce.domain.delivery.DeliveryAddress

data class OrderDto(
    var orderType: String = "",
    var paymentMethod: String = "",
    var requirement: String = "",
    var deliveryAddress: DeliveryAddress?,
    var orderMenuDtoList: MutableList<OrderMenuDto> = ArrayList()

)
