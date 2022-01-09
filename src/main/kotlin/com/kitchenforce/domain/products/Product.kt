package com.kitchenforce.domain.products

import com.kitchenforce.common.entity.AuditEntity
import com.kitchenforce.common.utils.SlangDictionary
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
@EntityListeners(AuditingEntityListener::class)
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    val name: String,

    @Column
    val price: Int,
) : AuditEntity() {
    init {
        checkValidPrice()
        checkValidProductName()
    }

    // TODO : 비즈니스 로직 관련 Exception 구현체가 필요한 것 같습니다. 일단 임시로 IllegalArgumentException 사용.
    private fun checkValidPrice() {
        if (price <0)
            throw IllegalArgumentException("상품의 가격은 0 미만이 될 수 없습니다.[$price]")
    }

    private fun checkValidProductName() {
        if (SlangDictionary.isSlang(name))
            throw IllegalArgumentException("상품 이름에 비속어는 포함될 수 없습니다.[$name]")
    }
}
