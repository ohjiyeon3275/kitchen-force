package com.kitchenforce.domain.delivery

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryRepository : JpaRepository<Delivery, Long> {

    fun findByAddress(address: String): Delivery?
}
