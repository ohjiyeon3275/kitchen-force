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

    @Column(name="phone_number", nullable = false)
    val phoneNumber : String,

    @Column(name="note")
    val note: String,

    @Column(name="rider", nullable=false)
    val rider: String

):AuditEntity()