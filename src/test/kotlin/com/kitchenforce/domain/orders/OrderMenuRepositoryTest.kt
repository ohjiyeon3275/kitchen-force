package com.kitchenforce.domain.orders

import com.kitchenforce.domain.orders.OrderMenu
import com.kitchenforce.domain.orders.OrderMenuRepository
import com.kitchenforce.domain.orders.OrderTable
import com.kitchenforce.domain.orders.OrderList
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
    fun create(){
        val orderInTable = OrderTable(1L)
        entityManager.persist(orderInTable)
        val orderList = OrderList("포장",10000L,"카드결제","30분후에 가지러갈게요.",orderInTable,1L)
        entityManager.persist(orderList)
        val orderMenu = OrderMenu("감자탕",8000L, 1L,orderList)
        entityManager.persist(orderMenu)
        entityManager.flush()
        val found = orderMenuRepository.findByIdOrNull(orderMenu.id!!)
        assertThat(found).isEqualTo(orderMenu)
    }

    @Test
    fun read(){
        val newMenu = orderMenuRepository.findById(1L)
        System.out.println(newMenu)
    }
}