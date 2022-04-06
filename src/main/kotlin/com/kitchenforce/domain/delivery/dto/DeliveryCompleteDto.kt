package com.kitchenforce.domain.delivery.dto

import com.kitchenforce.domain.delivery.Delivery
import com.kitchenforce.domain.delivery.Rider
import com.kitchenforce.domain.orders.Order

data class DeliveryCompleteDto(
    val deliveryId: Long,
    val deliveryStatus: String?,
    val address: String,
    val note: String?,
    val rider: Rider?,
    val order: Order?
) {
    companion object {
        fun fromDomain(delivery: Delivery): DeliveryCompleteDto =
            DeliveryCompleteDto(
                deliveryId = delivery.id ?: 0,
                deliveryStatus = delivery.deliveryStatus,
                address = delivery.address,
                note = delivery.note,
                rider = null,
                order = null,
            )
    }
}
