package com.kitchenforce.domain.orderTable

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class OrderTableRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val orderTableRepository: OrderTableRepository ){

    @Test
    fun `create`(){
        val order1 = OrderTable(1L)
        entityManager.persist(order1)
        entityManager.flush()
        val found = orderTableRepository.findByIdOrNull(order1.id!!)
        assertThat(found).isEqualTo(order1)
    }

    @Test
    fun `read`(){
        val newOrder =orderTableRepository.findById(1L)
        System.out.println(newOrder)
    }

}