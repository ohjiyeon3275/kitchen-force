package com.kitchenforce.domain.orders

import com.kitchenforce.domain.delivery.Delivery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OrderRepository : JpaRepository<Order, Long> {

    @Query(
        "select d from Order o " +
            "join Delivery d on o.delivery.id = d.id " +
            "where o.delivery.id = :deliveryId"
    )
    fun findByDeliveryExists(deliveryId: Long): Delivery?
}
