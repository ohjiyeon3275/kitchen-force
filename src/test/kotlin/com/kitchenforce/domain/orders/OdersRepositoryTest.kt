package com.kitchenforce.domain.orders

import com.kitchenforce.domain.orderTable.OrderTable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class OdersRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val ordersRepository: OrdersRepository
) {

    @Test
    fun `create`(){
        val orderInTable = OrderTable(1L)
        entityManager.persist(orderInTable)
        val orders = Orders("포장",10000L,"카드결제","30분후에 가지러갈게요.",orderInTable,1L)
        entityManager.persist(orders)
        entityManager.flush()
        val found = ordersRepository.findByIdOrNull(orders.id!!)
        assertThat(found).isEqualTo(orders)
    }

    @Test
    fun `read`(){
        val newOrders = ordersRepository.findById(1L)
        System.out.println(newOrders)
    }
}