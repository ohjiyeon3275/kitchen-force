package com.kitchenforce.domain.orders

import org.springframework.data.repository.CrudRepository

interface OrdersRepository: CrudRepository<Orders, Long> {
}