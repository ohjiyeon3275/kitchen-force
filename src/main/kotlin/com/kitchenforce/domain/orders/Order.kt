package com.kitchenforce.domain.orders

import com.kitchenforce.common.entity.AuditEntity
import com.kitchenforce.domain.enum.OrderStatus
import com.kitchenforce.domain.enum.OrderType
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @Column
    @Enumerated(EnumType.STRING)
    var orderType: OrderType,

    @Column
    var paymentPrice: Long,

    @Column
    var paymentMethod: String,

    @Column
    var requirement: String,

    @Column
    var deliveryAddress: String,

    @Column
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus,

    @ManyToOne
    @JoinColumn(name = "order_table_id")
    var orderTable: OrderTable,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

) : AuditEntity() {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    lateinit var orderMenuList: List<OrderMenu>
}
