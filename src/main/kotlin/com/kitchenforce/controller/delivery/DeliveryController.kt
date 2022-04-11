package com.kitchenforce.controller.delivery

import com.kitchenforce.domain.delivery.Delivery
import com.kitchenforce.domain.delivery.dto.DeliveryCompleteDto
import com.kitchenforce.service.delivery.DeliveryService
import org.springframework.web.bind.annotation.* // ktlint-disable no-wildcard-imports

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

        return deliveryService.updateStatusToComplete(deliveryId).run {
            DeliveryCompleteDto.fromDomain(this)
        }
    }
}
