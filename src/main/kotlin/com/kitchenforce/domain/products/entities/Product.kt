package com.kitchenforce.domain.products.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kitchenforce.common.entity.AuditEntity
import com.kitchenforce.common.utils.SlangDictionary
import com.kitchenforce.domain.products.dtos.ProductDto
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
    val id: Long? = null,

    @Column
    var name: String,

    @Column
    var price: Int,

    @Transient
    @JsonIgnore
    var slangDictionary: SlangDictionary?

) : AuditEntity() {
    init {
        checkValidProductName()
    }

    companion object {
        fun fromDto(dto: ProductDto, dictionary: SlangDictionary): Product = Product(
            id = dto.id,
            name = dto.name,
            price = dto.price,
            slangDictionary = dictionary
        )
    }

    private fun checkValidProductName() = slangDictionary?.run {
        if (isSlang(name))
            throw ProductException(ProductErrorCodeType.INVALID_PRODUCT_NAME)
    } ?: throw ProductException(ProductErrorCodeType.INIT_SLANG_DICT_FAILED)

    fun toDto(): ProductDto = ProductDto(
        id = id,
        name = name,
        price = price
    )
}
