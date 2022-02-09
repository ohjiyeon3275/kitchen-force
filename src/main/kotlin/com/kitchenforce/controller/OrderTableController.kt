package com.kitchenforce.controller

import com.kitchenforce.domain.orders.dto.OrderTableDto
import com.kitchenforce.service.OrderTableService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/order-table")
class OrderController (
    private val orderTableService: OrderTableService
){

    @PostMapping("")
    fun createOrderTable(@RequestBody dto: OrderTableDto){
        orderTableService.create(dto);
    }

    @GetMapping("/order-info/{userId}")
    fun orderInfo(@PathVariable userId: Long): OrderTableDto{
        return orderTableService.orderInfo(userId);
    }
}
