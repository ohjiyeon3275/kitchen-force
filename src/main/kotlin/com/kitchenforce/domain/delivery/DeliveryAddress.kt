package com.kitchenforce.domain.delivery

import com.kitchenforce.common.entity.AuditEntity
import javax.persistence.*

@Entity
@Table
class DeliveryAddress (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name="address", nullable = false)
    val address: String,

    @Column(nullable = false)
    val customerPhoneNumber : String,

    @Column(nullable = false)
    val status: String,

    @Column
    val note: String,

):AuditEntity()