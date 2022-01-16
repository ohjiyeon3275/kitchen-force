package com.kitchenforce.domain.orders

import org.springframework.data.repository.CrudRepository

interface OrderListRepository: CrudRepository<OrderList, Long> {
}