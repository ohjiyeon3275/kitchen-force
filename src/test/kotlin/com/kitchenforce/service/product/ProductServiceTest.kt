package com.kitchenforce.service.product

import com.kitchenforce.common.exception.NotFoundException
import com.kitchenforce.domain.products.dtos.ProductDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
internal class ProductServiceTest @Autowired constructor(
    val service: ProductService
) {

    @Nested
    @Transactional
    inner class ProductServiceCreateTest {
        @Test
        fun create_정상실행() {
            val vo = ProductDto(
                name = "제품1",
                price = 100,
            )
            service.create(vo)

            val list = service.findAll()
            assertEquals(list.size, 1)
            assertEquals(list.first().name, vo.name)
        }
    }

    @Nested
    @Transactional
    inner class ProductUpdateService {
        @Test
        fun update_정상_실행() {
            val created = service.create(ProductDto(name = "제품1", price = 100))
            service.update(created.id!!, ProductDto(name = created.name, price = 999))

            val list = service.findAll()
            assertAll(
                { assertEquals(1, list.size) },
                { assertEquals(created.name, list.first().name) },
                { assertEquals(999, list.first().price) }
            )
        }

        @Test
        fun update_인자의_id에_해당하는_상품이_없는_경우() {
            assertThrows<NotFoundException> {
                service.update(999, ProductDto(name = "제품1", price = 100))
            }
        }
    }
}
