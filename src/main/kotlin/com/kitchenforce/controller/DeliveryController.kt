package com.kitchenforce.controller

import com.kitchenforce.domain.delivery.Delivery
import com.kitchenforce.domain.delivery.dto.DeliveryCompleteDto
import com.kitchenforce.service.DeliveryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/delivery")
class DeliveryController(
    private val deliveryService: DeliveryService
) {

    @GetMapping("/list")
    fun getDeliveryList(): List<Delivery> {
        println("list 들어욤")
        return deliveryService.getDeliveryList()
    }

    @PutMapping("/{deliveryId}")
    fun updateStatusToComplete(@PathVariable deliveryId: Long): DeliveryCompleteDto {

        return deliveryService.updateStatusToComplete(deliveryId).run{
            DeliveryCompleteDto.fromDomain(this)
        }
    }
}
