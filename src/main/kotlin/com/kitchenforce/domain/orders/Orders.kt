package com.kitchenforce.domain.orders

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Orders(
    var orderType: String,
    var paymentPrice: Long,
    var paymentMethod: String,
    var requirement: String,

    /**
    @ManyToOne
    var orderTable: OrderTable,

    @OneToMany(fetch = FetchType.LAZY)
     var OrderMenuList : List<OrderMenu>
    **/

    var restaurantId: Long? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    var orderTime: LocalDateTime = LocalDateTime.now()
)