package com.kitchenforce.domain.products.entities

import com.kitchenforce.common.utils.SlangDictionary
import com.kitchenforce.domain.products.exception.ProductErrorCodeType
import com.kitchenforce.domain.products.exception.ProductException
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
internal class ProductEntityTest @Autowired constructor(
    @PersistenceContext
    val em: EntityManager
) {

    @MockK
    lateinit var slangDictionary: SlangDictionary

    @Test
    @DisplayName("정상적인 상품은 정상 등록 되어야 한다.")
    fun normalProductTest() {

        every { slangDictionary.isSlang(any()) } returns false

        assertDoesNotThrow {
            val prod = Product(null, "정상제품", 100, slangDictionary)
            assertEquals("정상제품", prod.name)
            assertEquals(100, prod.price)
        }
    }

    @Test
    @DisplayName("createdAt은 영속화 시점에 자동으로 세팅이 되어야 한다.")
    fun createdAtPersistTest() {

        every { slangDictionary.isSlang(any()) } returns false

        val prod = Product(null, "정상제품", 100, slangDictionary)
        em.persist(prod)

        println("prod createdAt : ${prod.createdAt}")
        assertNotNull(prod.createdAt)
    }

    @Test
    @DisplayName("상품명에 비속어가 들어가면 상품 등록이 되어서는 안된다.")
    fun productNameValidation() {
        every { slangDictionary.isSlang(any()) } returns true
        val exception = assertThrows<ProductException> {
            Product(null, "개똥", 100, slangDictionary)
        }
        println("Exception = ${exception.message}")
        assertEquals(ProductErrorCodeType.INVALID_PRODUCT_NAME.errorMessage, exception.errorMessage)
    }
}
