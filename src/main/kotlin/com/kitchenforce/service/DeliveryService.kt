package com.kitchenforce.service

import com.kitchenforce.domain.delivery.Delivery
import com.kitchenforce.domain.delivery.DeliveryRepository
import com.kitchenforce.domain.delivery.exception.DeliveryErrorCodeType
import com.kitchenforce.domain.delivery.exception.DeliveryException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DeliveryService(
    val deliveryAddressRepository: DeliveryRepository,
) {
    fun updateStatusToComplete(deliveryId: Long): Delivery {

        val delivery = deliveryAddressRepository.findByIdOrNull(deliveryId)
            ?: throw DeliveryException(DeliveryErrorCodeType.NOT_FOUND_DELIVERY)

        delivery.deliveryStatus = "배달완료"

        return deliveryAddressRepository.save(delivery)
    }
}
