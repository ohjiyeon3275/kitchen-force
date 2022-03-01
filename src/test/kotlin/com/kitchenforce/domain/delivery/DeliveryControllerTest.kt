package com.kitchenforce.domain.delivery

import com.fasterxml.jackson.databind.ObjectMapper
import com.kitchenforce.domain.delivery.exception.DeliveryErrorCodeType
import com.kitchenforce.domain.delivery.exception.DeliveryException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.put

@SpringBootTest
@AutoConfigureMockMvc
internal class DeliveryControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val deliveryAddressRepository: DeliveryAddressRepository,
    val riderRepository: RiderRepository
){

    @Test
    @DisplayName("배달완료한 주문의 상태를 바꾼다. -- 200")
    fun `배달완료한 주문의 상태를 바꾼다` () {

        //given
        val newDelivery =
            DeliveryAddress(1L,
                "주소시 주소동",
                "010123123",
                "주문완료",
                "조심히")

        val newRider =
            Rider(1L, "rider", "1231231234", listOf(newDelivery))

        deliveryAddressRepository.save(newDelivery)
        riderRepository.save(newRider)

        val riderId = 1L
        val deliveryId = 1L

        //when
        val performPut = mockMvc.put("/api/delivery/$riderId/$deliveryId"){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newRider)
        }

        //then
        performPut
            .andDo{ print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.status") { value("배달완료") }
            }
    }

    @Test
    @DisplayName("배달완료한 주문의 상태를 바꾼다. -- Exception")
    fun `배달완료한 주문의 상태를 바꾼다-exception` () {

        //given
        val newDelivery =
            DeliveryAddress(1L,
                "주소시 주소동",
                "010123123",
                "주문완료",
                "조심히")

        val newRider =
            Rider(1L, "rider", "1231231234", listOf(newDelivery))

        deliveryAddressRepository.save(newDelivery)
        riderRepository.save(newRider)

        val riderId = 1L
        val deliveryId = 2L // 이부분 오류뜨게 수정

        //when
        val performPut = mockMvc.put("/api/delivery/$riderId/$deliveryId"){
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newRider)
        }

        //then
        performPut
            .andDo{ print() }
            .andExpect {
                status { DeliveryException(DeliveryErrorCodeType.NOT_FOUND_DELIVERY) }
            }
    }
}
