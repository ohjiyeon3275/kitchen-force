package com.kitchenforce.domain.orderTable

import com.kitchenforce.domain.orderMenu.OrderMenu
import com.kitchenforce.domain.orders.Orders
import com.kitchenforce.domain.orders.OrdersRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class OrderTableRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val orderMenuRepository: OrdersRepository,
    val ordersRepository: OrdersRepository,
    val orderTableRepository: OrderTableRepository ){

    @Test
    fun `create`(){
        val orderInTable = OrderTable(1L)
        entityManager.persist(orderInTable)
        entityManager.flush()
        val found = orderTableRepository.findByIdOrNull(orderInTable.id!!)
        assertThat(found).isEqualTo(orderInTable)
    }

    @Test
    fun `read`(){
        val newOrder =orderTableRepository.findById(1L)
        System.out.println(newOrder)
    }

}