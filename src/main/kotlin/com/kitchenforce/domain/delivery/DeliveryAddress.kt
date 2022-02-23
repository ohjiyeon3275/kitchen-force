package com.kitchenforce.domain.delivery

import com.kitchenforce.common.entity.AuditEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
class DeliveryAddress(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var address: String,

    @Column(nullable = false)
    var customerPhoneNumber: String,

    @Column(nullable = false)
    var status: String,

    @Column
    var note: String,

) : AuditEntity()
