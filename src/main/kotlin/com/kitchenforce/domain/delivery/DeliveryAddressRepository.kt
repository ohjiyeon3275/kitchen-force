package com.kitchenforce.domain.delivery

import com.kitchenforce.domain.orders.OrderTable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryAddressRepository: JpaRepository<DeliveryAddress, Int> {

    fun findByAddress(address : String): DeliveryAddress?
}
