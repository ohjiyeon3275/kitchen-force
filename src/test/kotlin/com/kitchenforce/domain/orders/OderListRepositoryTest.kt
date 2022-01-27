package com.kitchenforce.domain.orders

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class OderListRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val ordersRepository: OrderListRepository
) {

    @Test
    fun create(){
        val orderInTable = OrderTable(1L)
        entityManager.persist(orderInTable)
        val orderList = OrderList("포장",10000L,"카드결제","30분후에 가지러갈게요.",orderInTable,1L)
        entityManager.persist(orderList)
        entityManager.flush()
        val found = ordersRepository.findByIdOrNull(orderList.id!!)
        assertThat(found).isEqualTo(orderList)
    }

    @Test
    fun read(){
        val newOrders = ordersRepository.findById(1L)
        System.out.println(newOrders)
    }
}