package com.kitchenforce.domain.delivery

import com.kitchenforce.controller.DeliveryController
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class DeliveryControllerTest {

    @Autowired
    lateinit var deliveryController: DeliveryController

    @Autowired
    lateinit var mvc: MockMvc

    @BeforeEach
    fun setUp() {
        mvc = MockMvcBuilders.standaloneSetup(deliveryController).build()
    }

    @Test
    fun controllerTest() {

        mvc.perform(MockMvcRequestBuilders.get("/api/delivery/get"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("hello get mapping :D"))
    }
}
