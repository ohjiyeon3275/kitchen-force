package com.kitchenforce.domain.orderMenu

import com.kitchenforce.domain.orders.Orders
import javax.persistence.*

@Entity
class OrderMenu(

    var menu: String,
    var price: Long,
    var quantity: Long,
    @ManyToOne var orders: Orders,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
)