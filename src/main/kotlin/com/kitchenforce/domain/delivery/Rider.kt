package com.kitchenforce.domain.delivery

import com.kitchenforce.common.entity.AuditEntity
import javax.persistence.*

@Entity
@Table
class Rider(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var phoneNumber: String,

    @OneToMany
    @JoinColumn(name = "delivery_address_id")
    var deliveryAddress: List<DeliveryAddress>

) : AuditEntity()
