package com.kitchenforce.controller

import com.kitchenforce.domain.orders.dto.OrderTableDto
import com.kitchenforce.service.OrderTableService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/order")
class OrderController (
    private val orderTableService: OrderTableService
){

    @PostMapping("/createTable")
    fun createOrderTable(@RequestBody dto: OrderTableDto){
        orderTableService.create(dto);
    }
}