package com.kitchenforce.domain.delivery

import com.kitchenforce.common.entity.AuditEntity
import javax.persistence.*

@Entity
@Table
class Rider (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,


    @Column(nullable = false)
    val name : String,

    @Column(nullable = false)
    val phoneNumber : String,

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    val deliveryAddress: DeliveryAddress

): AuditEntity()