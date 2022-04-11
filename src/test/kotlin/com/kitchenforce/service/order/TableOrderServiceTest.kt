package com.kitchenforce.service.order

import com.kitchenforce.domain.orders.OrderRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
class TableOrderServiceTest @Autowired constructor(
    val service: TableOrderService,
    val repository: OrderRepository,

) {

    @Transactional
    @Test
    @DisplayName("테이블 주문 생성 테스트")
    fun createTableOrderTest() {
    }
}
