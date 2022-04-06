package com.kitchenforce.domain.orders

import com.kitchenforce.common.entity.AuditEntity
import com.kitchenforce.domain.delivery.Delivery
import com.kitchenforce.domain.enum.OrderStatus
import com.kitchenforce.domain.enum.OrderType
import com.kitchenforce.domain.orders.dto.OrderDto
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

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
    var delivery: Delivery?,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

) : AuditEntity() {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    lateinit var orderMenuList: List<OrderMenu>

    companion object {
        fun generateTableOrder(
            orderType: OrderType,
            paymentMethod: String,
            paymentPrice: Long?,
            requirement: String
        ): Order = Order(
            orderStatus = OrderStatus.WAITING,
            orderType = orderType,
            paymentMethod = paymentMethod,
            paymentPrice = paymentPrice ?: 0,
            requirement = requirement,
            delivery = null

        )
    }

    fun toOrderDto(): OrderDto = OrderDto(
        orderType = orderType,
        orderStatus = orderStatus,
        paymentMethod = paymentMethod,
        requirement = requirement,
        price = paymentPrice,
        delivery = delivery ?: Delivery(-1, "", "", ""),
        orderTableDto = null,
        orderMenuDtoList = ArrayList()
    )
}
