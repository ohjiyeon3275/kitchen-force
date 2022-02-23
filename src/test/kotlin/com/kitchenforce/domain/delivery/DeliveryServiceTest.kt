package com.kitchenforce.domain.delivery

import com.kitchenforce.common.exception.BaseErrorCodeType
import com.kitchenforce.common.exception.BaseExceptionTemp
import org.junit.jupiter.api.TestInstance
import com.kitchenforce.service.DeliveryService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeliveryServiceTest @Autowired constructor (
    val emf : EntityManagerFactory
) {

    val deliveryAddressRepository : DeliveryAddressRepository = Mockito.mock(DeliveryAddressRepository::class.java)
    val riderRepository: RiderRepository = Mockito.mock(RiderRepository::class.java)

    var deliveryService: DeliveryService = Mockito.mock(DeliveryService::class.java)

    @BeforeEach
    fun setUp() {

        deliveryService = DeliveryService(deliveryAddressRepository, riderRepository)
        val em: EntityManager = emf.createEntityManager()

        em.transaction.begin()

        val deliveryAddressFirst = DeliveryAddress(
            address = "테스트시 주소동 123, 123호",
            customerPhoneNumber = "010-1234-1234",
            status = "주문완료",
            note = "리뷰이벤트"
        )

        val deliveryAddressSecond = DeliveryAddress(
            address = "테스트시 주소동 345, 345호",
            customerPhoneNumber = "010-5678-5678",
            status = "배달중",
            note = "맛있게"
        )

        val rider = Rider(
            name = "라이더",
            phoneNumber = "010-1234-5678",
            deliveryAddress = listOf(deliveryAddressFirst, deliveryAddressSecond)
        )

        em.persist(deliveryAddressFirst)
        em.persist(deliveryAddressSecond)
        em.persist(rider)

        em.transaction.commit()

    }


    @Test
    @DisplayName("배달 중인 주문만 배달 완료할 수 있다 --> fail")
    fun onlyOnDelivery() {

        val exception = assertThrows<BaseExceptionTemp> {
            // setup에서 커밋한 데이터를 Mock deliveryService에서 읽지 못함...
            deliveryService.updateStatusToComplete(1L, 1L)
        }

        Assertions.assertEquals(BaseErrorCodeType.NOT_MATCHED_DELIVERY_STATUS.errorMessage, exception.getErrorMessage())
    }
}