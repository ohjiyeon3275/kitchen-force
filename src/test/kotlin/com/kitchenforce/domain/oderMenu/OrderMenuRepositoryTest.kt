package com.kitchenforce.domain.oderMenu

import com.kitchenforce.domain.orderMenu.OrderMenu
import com.kitchenforce.domain.orderMenu.OrderMenuRepository
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
        var menu1 = OrderMenu("감자탕",8000L, 1L)
        entityManager.persist(menu1)
        entityManager.flush()
        var found = orderMenuRepository.findByIdOrNull(menu1.id!!)
        assertThat(found).isEqualTo(menu1)
    }

    @Test
    fun `read`(){
        var newMenu = orderMenuRepository.findById(1L)
        System.out.println(newMenu)
    }
}