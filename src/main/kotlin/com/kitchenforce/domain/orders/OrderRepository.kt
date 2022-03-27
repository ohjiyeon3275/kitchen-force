package com.kitchenforce.domain.orders

import com.kitchenforce.domain.delivery.DeliveryAddress
import com.kitchenforce.domain.orders.dto.OrderDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OrderRepository : JpaRepository<Order, Long> {

    @Query("select d from Order o " +
            "join DeliveryAddress d on o.deliveryAddress.id = d.id " +
            "where o.deliveryAddress.id = :deliveryAddressId")
    fun findByDeliveryAddressExists(deliveryAddressId: Long) : DeliveryAddress?
}
