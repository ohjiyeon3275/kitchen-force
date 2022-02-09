package com.kitchenforce.domain.orders

import com.kitchenforce.common.entity.AuditEntity
import com.kitchenforce.domain.menus.Menu
import com.kitchenforce.domain.orders.Order
import javax.persistence.*

@Entity
class OrderMenu(

    @Column
    var quantity: Long,

    @ManyToOne
    @JoinColumn(name = "orders_id")
    var order: Order,

    @ManyToOne
    @JoinColumn(name = "menu_id")
    var menu: Menu,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) : AuditEntity()
