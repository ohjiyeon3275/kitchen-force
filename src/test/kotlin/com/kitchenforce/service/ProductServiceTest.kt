package com.kitchenforce.service

import com.kitchenforce.domain.products.entities.Product
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import javax.validation.ConstraintViolationException

@SpringBootTest
@ActiveProfiles("test")
internal class ProductServiceTest @Autowired constructor(
    private val service: ProductService
) {

    // TODO : 요거 ktLint에서 빨간줄 뜨는데 혹시 확인 가능하실까요?
    @Nested
    inner class ProductServiceCreateTest {
        @Test
        @Transactional
        fun create_정상실행() {
            val vo = Product(
                name = "제품1",
                price = 100,
            )
            service.create(vo)

            val list = service.findAll()
            assertEquals(list.size, 1)
            assertEquals(list.first().name, vo.name)
        }

        @Test
        @Transactional
        fun create_가격_음수() {
            val vo = Product(
                name = "제품1",
                price = -1,
            )
            assertThrows<ConstraintViolationException> {
                service.create(vo)
            }
        }
    }
}
