package com.kitchenforce.domain.orderTable

import com.kitchenforce.domain.orders.Orders
import javax.persistence.*

@Entity
class OrderTable(
    var userId: Long,
    // @OneToMany(fetch = FetchType.LAZY) val ordersList: List<Orders>,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
)
