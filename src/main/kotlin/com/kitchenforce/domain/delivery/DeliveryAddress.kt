package com.kitchenforce.domain.delivery

import com.kitchenforce.common.entity.AuditEntity
import javax.persistence.*

@Table(name = "delivery_addresses")
@Entity
class DeliveryAddress (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var address: String,

    @Column(nullable = false)
    var phoneNumber: String,

    @Column(nullable = false)
    var accountStatus: String,

    @Column(nullable = false)
    var deliveryStatus: String,

    @Column
    var note: String,


) : AuditEntity()
