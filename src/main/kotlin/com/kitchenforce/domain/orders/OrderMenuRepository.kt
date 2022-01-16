package com.kitchenforce.domain.orders

import org.springframework.data.repository.CrudRepository

interface OrderMenuRepository: CrudRepository<OrderMenu, Long> {
}