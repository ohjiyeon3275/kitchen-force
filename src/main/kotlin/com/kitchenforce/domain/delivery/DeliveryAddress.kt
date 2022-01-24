package com.kitchenforce.domain.delivery

import com.kitchenforce.common.entity.AuditEntity
import javax.persistence.*

@Entity
@Table
class DeliveryAddress (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val address: String,

    @Column(nullable = false)
    val phoneNumber : String,

    @Column
    val note: String,

    @Column(nullable=false)
    val rider: String

):AuditEntity()