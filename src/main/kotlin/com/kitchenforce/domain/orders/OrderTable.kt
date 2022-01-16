package com.kitchenforce.domain.orders

import com.kitchenforce.common.entity.AuditEntity
import javax.persistence.*

@Entity
class OrderTable(

    var userId: Long,

    // @OneToMany(fetch = FetchType.LAZY) val ordersList: List<Orders>,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
) : AuditEntity()
