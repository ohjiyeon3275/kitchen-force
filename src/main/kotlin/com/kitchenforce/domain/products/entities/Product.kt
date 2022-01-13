package com.kitchenforce.domain.products.entities

import com.kitchenforce.common.entity.AuditEntity
import com.kitchenforce.common.utils.SlangDictionary
import com.kitchenforce.domain.products.exception.ProductErrorCodeType
import com.kitchenforce.domain.products.exception.ProductException
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
    val id: Int? = null,

    @Column
    val name: String,

    @Column
    val price: Int

) : AuditEntity() {
    init {
        checkValidPrice()
        checkValidProductName()
    }

    private fun checkValidPrice() {
        if (price <0)
            throw ProductException(ProductErrorCodeType.INVALID_PRICE)
    }

    private fun checkValidProductName() {
        if (SlangDictionary.isSlang(name))
            throw ProductException(ProductErrorCodeType.INVALID_PRODUCT_NAME)
    }
}
