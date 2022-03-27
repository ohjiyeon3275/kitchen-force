package com.kitchenforce.service

import com.kitchenforce.domain.enum.OrderStatus
import com.kitchenforce.domain.enum.OrderType
import com.kitchenforce.domain.menus.Menu
import com.kitchenforce.domain.menus.MenuGroup
import com.kitchenforce.domain.menus.MenuGroupRepository
import com.kitchenforce.domain.menus.MenuRepository
import com.kitchenforce.domain.orders.OrderMenuRepository
import com.kitchenforce.domain.orders.OrderRepository
import com.kitchenforce.domain.orders.OrderTableRepository
import com.kitchenforce.domain.orders.dto.OrderDto
import com.kitchenforce.domain.orders.dto.OrderMenuDto
import com.kitchenforce.domain.orders.dto.OrderTableDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

// 테스트를 위한 상수
const val TEST_NUM_OF_GUEST = 1
const val TEST_USER_ID = 1L

@SpringBootTest
@ActiveProfiles("test")
class OrderTableServiceTest @Autowired constructor(
    private val orderTableService: OrderTableService,
    private val orderService: OrderService,
    private val orderTableRepository: OrderTableRepository,
    private val orderRepository: OrderRepository,
    private val orderMenuRepository: OrderMenuRepository,
    private val menuRepository: MenuRepository,
    private val menuGroupRepository: MenuGroupRepository
) {
/*
    val testMenuGroup = MenuGroup(
        id = null,
        name = "메뉴그룹1"
    )

    val testMenu = Menu(
        id = null,
        name = "메뉴1",
        price = 1_000,
        isHidden = false,
        menuGroup = testMenuGroup
    )

    @BeforeEach
    fun setUpMenus() {
        menuGroupRepository.save(testMenuGroup)
        menuRepository.save(testMenu)
    }

    @Test
    @DisplayName("주문이 들어오면 OrderTable의 상태를 추가하고, Order 정보를 추가한다.")
    @Transactional
    fun createTest1() {

        val testOrderMenuDto = OrderMenuDto(
            quantity = 1L,
            menuName = "메뉴1"
        )
        val testOrderDto = OrderDto(
            orderType = "배달",
            paymentMethod = "카드결제",
            requirement = "?",
            deliveryAddress = "사랑시 고백구 행복동",
            orderMenuDtoList = mutableListOf(testOrderMenuDto)
        )
        val testOrderTableDto = OrderTableDto(
            userId = TEST_USER_ID,
            emptyness = false,
            tableName = "sample-Table",
            numberOfGuests = TEST_NUM_OF_GUEST,
            orderDtoList = mutableListOf(testOrderDto)
        )

        assertDoesNotThrow { orderTableService.create(testOrderTableDto) }

        val savedOrder = orderRepository.findAll().firstOrNull()
        val savedOrderTable = orderTableRepository.findByUserId(1L)
        val savedOrderMenu = orderMenuRepository.findAll().firstOrNull()

        assertAll(
            { assertNotNull(savedOrder) },
            { assertNotNull(savedOrder) },
            { assertEquals(testOrderDto.orderType, savedOrder?.orderType) },
            { assertNotNull(savedOrderTable) },
            { assertEquals(testOrderTableDto.tableName, savedOrderTable?.name) },
            { assertNotNull(savedOrderMenu) }
        )
    }

    @Test
    @DisplayName("userId로 주문 정보 조회시 정상적으로 결과 조회가 되어야 한다.")
    @Transactional
    fun orderInfoSuccess1() {
        setUpOrders()

        val result = assertDoesNotThrow { orderTableService.orderInfo(1L) }

        assertAll(
            { assertNotNull(result) },
            { assertEquals("테이블1", result.tableName) },
            { assertEquals(TEST_NUM_OF_GUEST, result.numberOfGuests) },
            { assertEquals(TEST_USER_ID, result.userId) },
            { assertFalse(result.orderDtoList.isEmpty()) },
            { assertNotNull(result.orderDtoList[0]) },
            { assertNotNull(result.orderDtoList[0].orderMenuDtoList[0]) },
        )
    }

    private fun setUpOrders() {
        val testOrderTable = OrderTable(
            userId = TEST_USER_ID,
            name = "테이블1",
            emptyness = true,
            numberOfGuests = TEST_NUM_OF_GUEST
        )

        val testOrder = Order(
            orderType = "배달",
            paymentPrice = 0,
            paymentMethod = "카드결제",
            requirement = "?",
            deliveryAddress = "사랑시 고백구 행복동",
            orderTable = testOrderTable
        )

        val testOrderMenu = OrderMenu(
            quantity = 1L,
            menu = testMenu,
            order = testOrder,
        )

        orderTableRepository.save(testOrderTable)
        orderRepository.save(testOrder)
        orderMenuRepository.save(testOrderMenu)

        testOrderTable.orderList = mutableListOf(testOrder)
        orderTableRepository.save(testOrderTable)

        testOrder.orderMenuList = mutableListOf(testOrderMenu)
        orderRepository.save(testOrder)
    }

 */
    companion object {

        private val testMenuGroup = MenuGroup(
            id = null,
            name = "메뉴그룹1"
        )

        private val testMenu1 = Menu(
            id = null,
            name = "메뉴1",
            price = 1_000,
            isHidden = false,
            menuGroup = testMenuGroup
        )

        private val orderMenuDto = OrderMenuDto(
            quantity = 1,
            menuName = "메뉴1"
        )

        private val orderTableDto = OrderTableDto(
            tableName = "1번",
            emptiness = false,
            numberOfGuests = 4
        )

        private val eatinOrderDto = OrderDto(
            orderType = OrderType.EATIN,
            orderStatus = OrderStatus.WAITING,
            paymentMethod = "card",
            requirement = "맛있게 부탁드려요 :)",
            deliveryAddress = "서울특별시 마포구 햇님아파트 210동 801호",
            orderTableDto = orderTableDto,
            orderMenuDtoList = listOf(orderMenuDto)
        )

        @BeforeAll
        @JvmStatic
        fun BeforeAll(
            @Autowired menuRepository: MenuRepository,
            @Autowired menuGroupRepository: MenuGroupRepository,
            @Autowired orderService: OrderService,
        ) {
            menuGroupRepository.save(testMenuGroup)
            menuRepository.save(testMenu1)

            orderService.create(eatinOrderDto)
        }
    }

    @Test
    @DisplayName("주문 테이블 목록을 조회할 수 있다.")
    @Transactional
    fun getOrderTableListTest() {

        val orderTableDtoList = orderTableService.get()

        assertThat(orderTableDtoList[0].tableName).isEqualTo("1번")
        assertThat(orderTableDtoList[0].emptiness).isEqualTo(false)
        assertThat(orderTableDtoList[0].numberOfGuests).isEqualTo(4)
    }

    @Test
    @DisplayName("테이블이 빈테이블인지 아닌지 확인할 수 있다.")
    @Transactional
    fun getEmptinessTest() {

        // 주문이 들어오지 않은 테이블의 경우, 빈테이블.
        val emptiness = orderTableService.get("2번")
        assertThat(emptiness).isEqualTo(true)

        // 주문이 들어온 테이블의 경우
        val notEmptiness = orderTableService.get("1번")
        assertThat(notEmptiness).isEqualTo(false)
    }

    @Test
    @DisplayName("방문한 손님 수를 변경할 수 있다.")
    @Transactional
    fun updateNumberOfGuestsTest() {

        val orderTable = orderTableRepository.findByIdOrNull(1L)
        assertThat(orderTable?.numberOfGuests).isEqualTo(4)

        // 1번 테이블의 손님수를 4명에서 5명으로 변경.
        orderTableService.update(5, "1번")
        val updatedOrderTable = orderTableRepository.findByIdOrNull(1L)
        assertThat(updatedOrderTable?.numberOfGuests).isEqualTo(5)
    }
}
