package com.kitchenforce.controller

import com.kitchenforce.domain.orders.dto.OrderDto
import com.kitchenforce.service.OrderService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/order")
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping("")
    fun createOrder(@RequestBody @Valid dto: OrderDto) {
        orderService.create(dto)
    }

    @GetMapping("")
    fun getOrderList(): List<OrderDto> {
        return orderService.get()
    }

    @PatchMapping("")
    fun updateStatus(id: Long) {
        orderService.update(id)
    }
}
