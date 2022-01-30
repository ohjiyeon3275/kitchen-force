package com.kitchenforce.domain.orders

import com.kitchenforce.common.entity.AuditEntity
import javax.persistence.*

@Entity
class OrderTable(

    @Column
    var userId: Long,

    @Column
    var name: String,
    
    @Column
    var emptyness: Boolean,

    @Column
    var numberOfGuests: Int,

    @OneToMany(fetch = FetchType.LAZY)
    var orderList: MutableList<Order> = ArrayList(),

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    var id: Long? = null
) : AuditEntity()
