package com.kitchenforce.domain.orders

import com.kitchenforce.common.entity.AuditEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @Column
    var orderType: String,

    @Column
    var paymentPrice: Long,

    @Column
    var paymentMethod: String,

    @Column
    var requirement: String,

    @Column
    var deliveryAddress: String,

    @ManyToOne
    @JoinColumn(name = "order_table_id")
    var orderTable: OrderTable,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    var orderMenuList : MutableList<OrderMenu> = ArrayList(),

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    var id: Long? = null,

) : AuditEntity()
