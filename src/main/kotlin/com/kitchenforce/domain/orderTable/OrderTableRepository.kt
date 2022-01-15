package com.kitchenforce.domain.orderTable

import org.springframework.data.repository.CrudRepository

interface OrderTableRepository: CrudRepository<OrderTable, Long> {

    fun findByUserId(userId : Long): OrderTable

}