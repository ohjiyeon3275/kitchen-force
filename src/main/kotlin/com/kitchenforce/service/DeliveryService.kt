package com.kitchenforce.service

import com.kitchenforce.common.exception.BaseErrorCodeType
import com.kitchenforce.common.exception.BaseExceptionTemp
import com.kitchenforce.domain.delivery.DeliveryAddress
import com.kitchenforce.domain.delivery.DeliveryAddressRepository
import com.kitchenforce.domain.delivery.RiderRepository
import org.springframework.stereotype.Service

@Service
class DeliveryService(
    val deliveryAddressRepository: DeliveryAddressRepository,
    val riderRepository: RiderRepository
) {

    fun updateStatusToComplete (riderId: Long, deliveryAddressId: Long) : DeliveryAddress {

        val riderOptional = riderRepository.findById(riderId)

        if(riderOptional.isEmpty) {
             throw BaseExceptionTemp(BaseErrorCodeType.NOT_FOUND_RIDER)
        }

        val rider = riderOptional.get()

        if(rider.deliveryAddress[deliveryAddressId.toInt()] == null){
            throw BaseExceptionTemp(BaseErrorCodeType.NOT_FOUND_DELIVERY)
        }

        val delivery = rider.deliveryAddress[deliveryAddressId.toInt()]

        if (delivery.status != "배달중") {
            throw BaseExceptionTemp(BaseErrorCodeType.NOT_MATCHED_DELIVERY_STATUS)
        }else{
            delivery.status = "배달완료"
            return deliveryAddressRepository.save(delivery)
        }

        return delivery
    }
}
