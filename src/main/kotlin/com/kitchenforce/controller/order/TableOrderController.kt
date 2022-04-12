package com.kitchenforce.controller.order

import com.kitchenforce.domain.orders.dto.OrderDto
import com.kitchenforce.service.order.AbstractOrderService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/order/table")
class TableOrderController(
    @Qualifier("tableOrderService")
    val orderService: AbstractOrderService,
) {

    @PostMapping("")
    fun createOrder(@RequestBody @Valid dto: OrderDto) {
        orderService.createOrder(dto)
    }

    @GetMapping("")
    fun getOrderList(): List<OrderDto> {
        return orderService.getAllOrders()
    }

    @PutMapping("/{id}")
    fun updateStatus(@PathVariable id: Long, @RequestBody orderDto: OrderDto?) {
        orderService.updateOrder(id, orderDto)
    }
}
