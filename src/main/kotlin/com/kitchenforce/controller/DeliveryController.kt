package com.kitchenforce.controller

import com.kitchenforce.domain.delivery.dto.DeliveryCompleteDto
import com.kitchenforce.service.DeliveryService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/delivery")
class DeliveryController(
    private val deliveryService: DeliveryService
) {

    @PutMapping("/{deliveryId}")
    fun updateStatusToComplete(@PathVariable deliveryId: Long): DeliveryCompleteDto {

        return deliveryService.updateStatusToComplete(deliveryId).run{
            DeliveryCompleteDto.fromDomain(this)
        }
    }
}
