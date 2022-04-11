package com.kitchenforce.domain.orders

import com.kitchenforce.common.entity.AuditEntity
import javax.persistence.* // ktlint-disable no-wildcard-imports

@Entity
class OrderTable(

    @Column
    var name: String,

    @Column
    var emptiness: Boolean,

    @Column
    var numberOfGuests: Int,

    @OneToOne
    @JoinColumn(name = "orders_id")
    var order: Order?,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) : AuditEntity()
