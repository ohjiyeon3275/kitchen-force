package com.kitchenforce.domain.orders

import com.kitchenforce.domain.delivery.DeliveryAddress
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
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus,

    @Column
    var paymentPrice: Long,

    @Column
    var paymentMethod: String,

    @Column
    var requirement: String,

    @OneToOne
    var deliveryAddress: DeliveryAddress?,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

) : AuditEntity() {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    lateinit var orderMenuList: List<OrderMenu>
}
