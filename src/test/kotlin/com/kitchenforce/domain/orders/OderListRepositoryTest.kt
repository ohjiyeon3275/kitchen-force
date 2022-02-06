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
class OderListRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val ordersRepository: OrderRepository
) {

    @Test
    @Transactional
    fun create() {
        val orderInTable = OrderTable(
            userId = 1L,
            emptyness = true,
            numberOfGuests = 3
        )

        // orderTable의 PK를 알아야함.
        val savedOrderTable = entityManager.persist(orderInTable)

        val order = Order(
            "포장",
            10000L,
            "카드결제",
            "30분후에 가지러갈게요.",
            savedOrderTable,
            emptyList()
        )

        val savedOrder = entityManager.persist(order)

        orderInTable.orderList = listOf(savedOrder)

        entityManager.persist(orderInTable)

        val orderMenu = OrderMenu(
            id = null,
            price = 1000L,
            quantity = 10L,
            order = savedOrder
        )

        val orderMenuList = listOf(
            orderMenu
        )

        savedOrder.orderMenuList = orderMenuList

        entityManager.persist(savedOrder)
        entityManager.persist(orderMenu)
        entityManager.flush()
//        entityManager.clear()

        val found = ordersRepository.findByIdOrNull(order.id)
        assertThat(found).isEqualTo(order)
//        assertThat(found!!.id).isEqualTo(order.id)
    }

    @Test
    fun read() {
        val newOrders = ordersRepository.findById(1L)
        println(newOrders)
    }
}
