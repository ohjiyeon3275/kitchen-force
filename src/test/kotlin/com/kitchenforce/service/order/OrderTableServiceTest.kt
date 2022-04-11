package com.kitchenforce.service.order

import com.kitchenforce.domain.enum.OrderStatus
import com.kitchenforce.domain.enum.OrderType
import com.kitchenforce.domain.orders.Order
import com.kitchenforce.domain.orders.OrderRepository
import com.kitchenforce.domain.orders.OrderTable
import com.kitchenforce.domain.orders.OrderTableRepository
import com.kitchenforce.domain.orders.exception.OrderErrorCodeType
import com.kitchenforce.domain.orders.exception.OrderException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
class OrderTableServiceTest @Autowired constructor(
    private val service: OrderTableService,
    private val repository: OrderTableRepository,
    private val orderRepository: OrderRepository
) {

    private val testOrder = Order(
        orderType = OrderType.EATIN,
        orderStatus = OrderStatus.ACCEPTED,
        paymentMethod = "현금",
        paymentPrice = 10_000L,
        delivery = null,
        requirement = "웰던으로"
    )

    private val testOrderTableList = listOf(
        OrderTable(
            name = "table1",
            emptiness = true,
            numberOfGuests = 0,
            order = null,
        ),
        OrderTable(
            name = "table2",
            emptiness = false,
            numberOfGuests = 3,
            order = orderRepository.save(testOrder),
        ),
        OrderTable(
            name = "table3",
            emptiness = true,
            numberOfGuests = 0,
            order = null,
        ),
    )
    private fun saveTestList(list: List<OrderTable>): List<OrderTable> {

        return repository.saveAll(list)
    }

    @Test
    @DisplayName("주문 테이블 전체 조회 테스트")
    @Transactional
    fun findAllOrderTableListTest() {
        val expects = saveTestList(testOrderTableList)

        val result = service.getAll()
        assertAll(
            { assertEquals(3, result.size) },
            { assertEquals(expects.first { it.name == "table1" }.name, result.first { it.tableName == "table1" }.tableName) }
        )
    }

    @Test
    @DisplayName("주문 테이블 단건 조회 테스트 - 빈 테이블 조회")
    @Transactional
    fun findOrderTableWithEmptyStatus() {
        val inputList = saveTestList(testOrderTableList)

        val expect = inputList.first { it.emptiness }
        val result = service.get(expect.name)
        assertAll(
            { assertEquals(expect.name, result!!.tableName) },
            { assertTrue(result!!.emptiness) },
        )
    }

    @Test
    @DisplayName("테이블 상태 수정")
    @Transactional
    fun updateOrderTableNumbersOfGuests() {
        val inputList = saveTestList(testOrderTableList)

        assertEquals(
            testOrderTableList.first { it.name == "table2" }.numberOfGuests,
            inputList.first { it.name == "table2" }.numberOfGuests
        )

        service.updateOccupiedTable(5, "table2")

        assertEquals(
            5,
            repository.findByIdOrNull(inputList.first { it.name == "table2" }.id!!)!!.numberOfGuests
        )
    }

    @Test
    @DisplayName("테이블 상태 수정 실패")
    @Transactional
    fun updateOrderTableNumbersOfGuests_orderTableNotFound() {
        saveTestList(testOrderTableList)

        val exception = assertThrows<OrderException> { service.updateOccupiedTable(5, "table9999") }
        assertEquals(OrderErrorCodeType.ORDER_TABLE_NOT_FOUND.errorStatus, exception.errorStatus)
    }
}
