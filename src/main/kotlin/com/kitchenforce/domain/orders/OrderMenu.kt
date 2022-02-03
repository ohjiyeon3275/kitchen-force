package com.kitchenforce.domain.orders

import com.kitchenforce.common.entity.AuditEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class OrderMenu(

    @Column
    var price: Long,

    @Column
    private var quantity: Long,

    @ManyToOne
    @JoinColumn(name = "orders_id")
    var order: Order?,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
) : AuditEntity()
