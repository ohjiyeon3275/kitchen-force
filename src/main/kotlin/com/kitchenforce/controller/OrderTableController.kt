package com.kitchenforce.controller

import com.kitchenforce.domain.orders.dto.OrderTableDto
import com.kitchenforce.service.OrderTableService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/order-table")
class OrderTableController(
    private val orderTableService: OrderTableService
) {

    @GetMapping("")
    fun getOrderTableList(): List<OrderTableDto> {
        return orderTableService.get()
    }
}
