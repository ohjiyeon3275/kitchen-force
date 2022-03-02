package com.kitchenforce.controller

import com.kitchenforce.domain.delivery.DeliveryAddress
import com.kitchenforce.service.DeliveryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/delivery")
class DeliveryController(
    private val deliveryService: DeliveryService
) {

    @PutMapping("/{deliveryId}")
    fun updateStatusToComplete(@PathVariable deliveryId: Long): DeliveryAddress {

        return deliveryService.updateStatusToComplete(deliveryId)
    }
}
