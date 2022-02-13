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
import org.springframework.test.context.ActiveProfiles
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeliveryServiceTest @Autowired constructor (
    private var deliveryService: DeliveryService
) {

//    val deliveryAddressRepository : DeliveryAddressRepository = Mockito.mock(DeliveryAddressRepository::class.java)
//    val riderRepository: RiderRepository = Mockito.mock(RiderRepository::class.java)

    val emf : EntityManagerFactory = Mockito.mock(EntityManagerFactory::class.java)

    @BeforeAll
    fun setUp() {


        val em: EntityManager = emf.createEntityManager()
        em.transaction.begin()

        val deliveryAddressFirst = DeliveryAddress (
            address = "123, 123ho",
            customerPhoneNumber = "010-1234-1234",
            status = "complete",
            note = "review ")

        em.persist(deliveryAddressFirst)

        val deliveryAddressSecond = DeliveryAddress (
            address = "345, 345ho",
            customerPhoneNumber = "010-5678-5678",
            status = "onDelivery",
            note = "please"
        )

        em.persist(deliveryAddressSecond)

        val rider = Rider (
            name = "riders",
            phoneNumber = "010-5678-5678",
            deliveryAddress = listOf(deliveryAddressFirst, deliveryAddressSecond)
        )

        em.persist(rider)
        em.transaction.commit()

    }


    @Test
    @DisplayName("배달 중인 주문만 배달 완료할 수 있다")
    fun onlyOnDelivery() {

        val em = emf.createEntityManager()

        val exception = assertThrows<BaseExceptionTemp> {
            // 서비스 내에서 setUp 데이터 못찾음...왜??...?????/
            deliveryService.updateStatusToComplete(1L, 1L)
        }

        Assertions.assertEquals(BaseErrorCodeType.NOT_MATCHED_DELIVERY_STATUS.errorMessage, exception.getErrorMessage())

    }
}