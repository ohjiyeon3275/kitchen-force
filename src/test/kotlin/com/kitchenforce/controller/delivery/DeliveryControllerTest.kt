package com.kitchenforce.controller.delivery

import com.kitchenforce.domain.delivery.Delivery
import com.kitchenforce.domain.delivery.DeliveryRepository
import com.kitchenforce.domain.delivery.Rider
import com.kitchenforce.domain.delivery.RiderRepository
import com.kitchenforce.domain.delivery.exception.DeliveryErrorCodeType
import com.kitchenforce.domain.delivery.exception.DeliveryException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.put

// given
val newDelivery =
    Delivery(
        id = 1L,
        deliveryStatus = "배송중",
        address = "주소",
        note = "note",
    )

@SpringBootTest
@AutoConfigureMockMvc
internal class DeliveryControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val deliveryRepository: DeliveryRepository,
    val riderRepository: RiderRepository
) {

    @Test
    @DisplayName("배달완료한 주문의 상태를 바꾼다. -- 200")
    fun `배달완료한 주문의 상태를 바꾼다`() {

        val newRider =
            Rider(1L, "rider", "1231231234", listOf(newDelivery))

        deliveryRepository.save(newDelivery)
        riderRepository.save(newRider)

        val deliveryId = 1L

        // when
        val performPut = mockMvc.put("/api/delivery/$deliveryId")

        // then
        performPut
            .andDo { print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.deliveryStatus") { value("배달완료") }
            }
    }

    @Test
    @DisplayName("배달완료한 주문의 상태를 바꾼다. -- Exception")
    fun `배달완료한 주문의 상태를 바꾼다-exception`() {

        val newRider =
            Rider(1L, "rider", "1231231234", listOf(newDelivery))

        deliveryRepository.save(newDelivery)
        riderRepository.save(newRider)

        val deliveryId = 2L // 이부분 오류뜨게 수정

        // when
        val performPut = mockMvc.put("/api/delivery/$deliveryId")

        // then
        performPut
            .andDo { print() }
            .andExpect {
                status { DeliveryException(DeliveryErrorCodeType.NOT_FOUND_DELIVERY) }
            }
    }
}
