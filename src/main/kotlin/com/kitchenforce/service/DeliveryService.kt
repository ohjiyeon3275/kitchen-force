package com.kitchenforce.service

import com.kitchenforce.domain.delivery.DeliveryAddress
import com.kitchenforce.domain.delivery.DeliveryAddressRepository
import com.kitchenforce.domain.delivery.RiderRepository
import com.kitchenforce.domain.delivery.exception.DeliveryErrorCodeType
import com.kitchenforce.domain.delivery.exception.DeliveryException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DeliveryService(
    val deliveryAddressRepository: DeliveryAddressRepository,
    val riderRepository: RiderRepository

) {
    fun updateStatusToComplete(riderId: Long, deliveryId: Long): DeliveryAddress {

        val delivery = deliveryAddressRepository.findByIdOrNull(deliveryId)
            ?: throw DeliveryException(DeliveryErrorCodeType.NOT_FOUND_DELIVERY)

        riderRepository.findByIdOrNull(riderId)
            ?: throw DeliveryException(DeliveryErrorCodeType.NOT_FOUND_RIDER)

        delivery.status = "배달완료"

        return deliveryAddressRepository.save(delivery)
    }
}