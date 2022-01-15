package com.kitchenforce.domain.oderMenu

import com.kitchenforce.domain.orderMenu.OrderMenu
import com.kitchenforce.domain.orderMenu.OrderMenuRepository
import com.kitchenforce.domain.orderTable.OrderTable
import com.kitchenforce.domain.orders.Orders
import com.kitchenforce.domain.orders.OrdersRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class OrderMenuRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val orderMenuRepository: OrderMenuRepository
){
    @Test
    fun `create`(){
        val orderInTable = OrderTable(1L)
        entityManager.persist(orderInTable)
        val orders = Orders("포장",10000L,"카드결제","30분후에 가지러갈게요.",orderInTable,1L)
        entityManager.persist(orders)
        val orderMenu = OrderMenu("감자탕",8000L, 1L,orders)
        entityManager.persist(orderMenu)
        entityManager.flush()
        var found = orderMenuRepository.findByIdOrNull(orderMenu.id!!)
        assertThat(found).isEqualTo(orderMenu)
    }

    @Test
    fun `read`(){
        var newMenu = orderMenuRepository.findById(1L)
        System.out.println(newMenu)
    }
}