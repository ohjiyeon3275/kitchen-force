package com.kitchenforce.controller

import com.kitchenforce.domain.delivery.DeliveryAddress
import com.kitchenforce.service.DeliveryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/delivery")
class DeliveryController(
    private val deliveryService: DeliveryService
) {

    @GetMapping("/list")
    fun getDeliveryList(): List<DeliveryAddress> {
        println("list 들어욤")
        return deliveryService.getDeliveryList();
    }

    @PutMapping("/{deliveryId}")
    fun updateStatusToComplete(@PathVariable deliveryId: Long): DeliveryAddress {

        return deliveryService.updateStatusToComplete(deliveryId)
    }
}
