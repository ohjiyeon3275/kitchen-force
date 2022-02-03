package com.kitchenforce.domain.orders

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@DataJpaTest
@ActiveProfiles("test")
class OrderMenuRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val orderMenuRepository: OrderMenuRepository
) {
    @Test
    @Transactional
    fun create() {
        val orderInTable = OrderTable(
            userId = 1L,
            emptyness = true,
            numberOfGuests = 3
        )

        entityManager.persist(orderInTable)

        val order = Order(
            "포장",
            10000L,
            "카드결제",
            "30분후에 가지러갈게요.",
            orderInTable,
            emptyList(),
        )

        orderInTable.orderList = listOf(order)

        val orderMenu = OrderMenu(
            price = 8000L,
            quantity = 1L,
            order
        )

        entityManager.persist(order)
        entityManager.persist(orderMenu)
        entityManager.flush()
        val found = orderMenuRepository.findByIdOrNull(orderMenu.id!!)
        assertThat(found).isEqualTo(orderMenu)
    }

    @Test
    fun read() {

        val newMenu = orderMenuRepository.findById(1L)
        System.out.println(newMenu)
    }
}
