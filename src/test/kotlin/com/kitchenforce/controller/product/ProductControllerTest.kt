package com.kitchenforce.controller.product

import com.fasterxml.jackson.databind.ObjectMapper
import com.kitchenforce.common.utils.SlangDictionary
import com.kitchenforce.domain.products.entities.Product
import com.kitchenforce.service.product.ProductService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @Qualifier("slangDictionary") private val slangDictionary: SlangDictionary,
    @Qualifier("commonObjectMapper") private val objectMapper: ObjectMapper,
) {

    companion object {
        const val PRODUCT_NAME1 = "Product1"
        const val PRODUCT_NAME2 = "Product2"
        const val PRODUCT_NAME3 = "Product3"
    }

    private val testProduct = Product(
        id = 1L,
        name = PRODUCT_NAME1,
        price = 10_000,
        slangDictionary = slangDictionary
    )

    private val testLowPriceProduct = Product(
        id = 1L,
        name = PRODUCT_NAME1,
        price = -100,
        slangDictionary = slangDictionary
    )

    private val testProductList = listOf(
        Product(
            id = 1L,
            name = PRODUCT_NAME1,
            price = 10_000,
            slangDictionary = slangDictionary
        ),
        Product(
            id = 2L,
            name = PRODUCT_NAME2,
            price = 20_000,
            slangDictionary = slangDictionary
        ),
        Product(
            id = 3L,
            name = PRODUCT_NAME3,
            price = 30_000,
            slangDictionary = slangDictionary
        )
    )

    @MockkBean
    private lateinit var productService: ProductService

    @Test
    @DisplayName("전체 프로덕트 조회")
    fun findAllProducts_ApiTest() {
        every { productService.findAll() } returns testProductList.map { it.toDto() }

        mockMvc.perform(
            get(ProductController.PRODUCT_API_PATH)
        )
            .andExpect(status().isOk)
            .andExpect {
                content().contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.[0].name").value(`is`(PRODUCT_NAME1))
                jsonPath("$.[1].name").value(`is`(PRODUCT_NAME2))
                jsonPath("$.[2].name").value(`is`(PRODUCT_NAME3))
                jsonPath("$.[0].price").value(`is`(10_000))
            }
    }

    @Test
    @DisplayName("상품 등록(INSERT) 테스트")
    fun createNewProduct_APITest() {

        every { productService.create(any()) } returns testProduct.toDto()

        mockMvc.perform(
            post(ProductController.PRODUCT_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testProduct))
        )
            .andExpect(status().isOk)
            .andExpect {
                content().contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.name").value(`is`(PRODUCT_NAME1))
                jsonPath("$.price").value(`is`(10_000))
            }
    }

    @Test
    @DisplayName("상품 등록(INSERT) Validation 테스트")
    fun createNewProduct_APITest_validation_failed() {
        every { productService.create(any()) } returns testLowPriceProduct.toDto()

        mockMvc.perform(
            post(ProductController.PRODUCT_API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testLowPriceProduct))
        )
            .andExpect(status().isBadRequest)
            .andExpect {
                content().contentType(MediaType.APPLICATION_JSON)
            }
    }

    @Test
    @DisplayName("상품 수정(Update) 테스트")
    fun updateNewProduct_APITest_validation_failed() {
        every { productService.update(any(), any()) } returns testProduct.toDto()

        mockMvc.perform(
            put(ProductController.PRODUCT_API_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testLowPriceProduct))
        )
            .andExpect(status().isOk)
            .andExpect {
                content().contentType(MediaType.APPLICATION_JSON)
                jsonPath("$.name").value(`is`(PRODUCT_NAME1))
                jsonPath("$.price").value(`is`(10_000))
            }
    }
}
