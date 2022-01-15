package com.kitchenforce.domain.orderTable

import javax.persistence.*

@Entity
class OrderTable(
    var userId: Long,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null

    /*
    @OneToMany(fetch = FetchType.LAZY)
    var ordersList: List<Orders>

     */
)
