package com.kitchenforce.controller

import com.kitchenforce.domain.orders.dto.OrderDto
import com.kitchenforce.domain.orders.dto.OrderTableDto
import com.kitchenforce.service.OrderService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/order")
class OrderController (
    private val orderService: OrderService
        ){

    @PostMapping("")
    fun createOrder(@RequestBody dto: OrderDto) {
        orderService.create(dto)
    }

    @GetMapping("")
    fun getOrderList(): List<OrderDto> {
        return orderService.get()
    }

    @GetMapping("/{userId}")
    fun getOrder(@PathVariable userId: Long): OrderDto {
        return orderService.getOrder(userId)
    }

}