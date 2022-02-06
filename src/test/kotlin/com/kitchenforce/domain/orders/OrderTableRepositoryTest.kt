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
class OrderTableRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val orderTableRepository: OrderTableRepository
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
        entityManager.flush()
        val found = orderTableRepository.findByIdOrNull(orderInTable.id!!)
        assertThat(found).isEqualTo(orderInTable)
    }

    @Test
    @Transactional
    fun read() {

        val orderInTable = OrderTable(
            userId = 1L,
            emptyness = true,
            numberOfGuests = 3
        )

        val savedTable = entityManager.persist(orderInTable)
        entityManager.flush()
        entityManager.clear()

        val newOrder = orderTableRepository.findById(savedTable.id!!)
        System.out.println(newOrder)
    }
}
