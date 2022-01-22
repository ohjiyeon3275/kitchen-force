package com.kitchenforce.domain.orders

import org.springframework.data.repository.JpaRepository

interface OrderTableRepository: JpaRepository<OrderTable, Long> {

    fun findByUserId(userId : Long): OrderTable?

}
