package com.kitchenforce.domain.delivery

import com.kitchenforce.service.DeliveryService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import javax.persistence.EntityManagerFactory

@DataJpaTest
@ActiveProfiles("test")
class DeliveryAddressTest @Autowired constructor (
    val emf : EntityManagerFactory,
) {

    @BeforeEach
    fun setUpData() {

        val em = emf.createEntityManager()
        em.transaction.begin()

        val deliveryAddressFirst = DeliveryAddress(
            address = "테스트시 주소동 123, 123호",
            phoneNumber = "010-1234-1234",
            accountStatus = "",
            deliveryStatus = "주문완료",
            note = "리뷰이벤트"
        )

        val deliveryAddressSecond = DeliveryAddress(
            address = "테스트시 주소동 345, 345호",
            phoneNumber = "010-5678-5678",
            accountStatus = "",
            deliveryStatus = "배달중",
            note = "맛있게"
        )


        val rider = Rider(
            name = "라이더",
            phoneNumber = "010-5678-5678",
            deliveryAddress = listOf(deliveryAddressFirst, deliveryAddressSecond)
        )

        em.persist(deliveryAddressFirst)
        em.persist(deliveryAddressSecond)
        em.persist(rider)

        em.transaction.commit()

        em.clear()
        em.close()
    }


    @Test
    @DisplayName("주문을 배달 완료한다.")
    fun completeDelivery() {

        val em = emf.createEntityManager()
        val rider = em.find(Rider::class.java, 1L)

        Assertions.assertEquals(
            "배달중", em.find(DeliveryAddress::class.java, 2L).deliveryStatus
        )

        // 1번째 (pk == 2) status 변경
        rider.deliveryAddress[1].deliveryStatus = "배달완료"

        em.persist(rider)

        Assertions.assertNotEquals(
            "배달완료", em.find(DeliveryAddress::class.java, 1L).deliveryStatus
        )
        Assertions.assertEquals(
            "배달완료", em.find(DeliveryAddress::class.java, 2L).deliveryStatus
        )

    }





}