package com.kitchenforce.domain.orders

import org.springframework.data.jpa.repository.JpaRepository

interface OrderTableRepository : JpaRepository<OrderTable, Long> {
    fun findByNameAndEmptiness(name: String, emptiness: Boolean): OrderTable?
    fun findByOrderAndEmptiness(order: Order, b: Boolean): OrderTable?
}
