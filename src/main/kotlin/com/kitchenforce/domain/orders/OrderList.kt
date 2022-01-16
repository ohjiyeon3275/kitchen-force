package com.kitchenforce.domain.orders

import com.kitchenforce.common.entity.AuditEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class OrderList(
    @Column
    var orderType: String,

    @Column
    var paymentPrice: Long,

    @Column
    var paymentMethod: String,

    @Column
    var requirement: String,

    @ManyToOne
    @JoinColumn(name = "order_table_id")
    var orderTable: OrderTable,

    // @OneToMany(fetch = FetchType.LAZY) val OrderMenuList : List<OrderMenu>,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,

    @Column
    var orderTime: LocalDateTime = LocalDateTime.now()
) : AuditEntity()