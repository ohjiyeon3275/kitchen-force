package com.kitchenforce.domain.orders

import com.kitchenforce.common.entity.AuditEntity
import javax.persistence.*

@Entity
class OrderTable(

    @Column
    var userId: Long,
    
    @Column
    var emptyness: Boolean,

    @Column
    var numberOfGuests: Int,

    // @OneToMany(fetch = FetchType.LAZY) val orderList: List<Order>,

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    var id: Long? = null
) : AuditEntity()
