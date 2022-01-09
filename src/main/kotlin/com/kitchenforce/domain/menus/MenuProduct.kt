package com.kitchenforce.domain.menus

import com.kitchenforce.common.entity.AuditEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table
class MenuProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @Column(nullable = false)
    val quantity: Int,

    @ManyToOne
    @JoinColumn(name = "menu_id")
    val menu: Menu,

    // TODO: Product 구현시 추가 예정
//    val product: Product,
) : AuditEntity()
