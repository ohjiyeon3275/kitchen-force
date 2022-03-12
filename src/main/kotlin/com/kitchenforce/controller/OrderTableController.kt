package com.kitchenforce.controller

import com.kitchenforce.domain.orders.dto.OrderTableDto
import com.kitchenforce.service.OrderTableService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Min

@Validated
@RestController
@RequestMapping("/api/order-table")
class OrderTableController(
    private val orderTableService: OrderTableService
) {

    @GetMapping("")
    fun getOrderTableList(): List<OrderTableDto> {
        return orderTableService.get()
    }

    @GetMapping("/{tableName}")
    fun getEmptiness(@PathVariable tableName: String): Boolean {
        return orderTableService.get(tableName)
    }

    @PatchMapping("")
    fun updateNumberOfGuests(@Min(0) numberOfGuests: Int, tableName: String) {
        orderTableService.update(numberOfGuests, tableName)
    }
}
