package com.kitchenforce.domain.orderMenu

import javax.persistence.*

@Entity
class OrderMenu(

    var menu: String,
    var price: Long,
    var quantity: Long,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null

    /**
    @ManyToOne
    var orders: Orders
    **/
)