package com.kitchenforce.domain.orders

import org.springframework.data.repository.CrudRepository

interface OrderRepository: CrudRepository<Order, Long> {
}
