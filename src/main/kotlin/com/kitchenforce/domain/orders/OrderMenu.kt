package com.kitchenforce.domain.orders

import com.kitchenforce.common.entity.AuditEntity
import com.kitchenforce.domain.orders.OrderList
import javax.persistence.*

@Entity
class OrderMenu(

    @Column
    var menu: String,

    @Column
    var price: Long,

    @Column
    var quantity: Long,

    @ManyToOne
    @JoinColumn(name = "order_list_id")
    var orderList: OrderList,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
) : AuditEntity()