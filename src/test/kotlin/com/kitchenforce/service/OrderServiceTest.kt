package com.kitchenforce.service

import com.kitchenforce.domain.delivery.Delivery
import com.kitchenforce.domain.enum.OrderStatus
import com.kitchenforce.domain.enum.OrderType
import com.kitchenforce.domain.menus.Menu
import com.kitchenforce.domain.menus.MenuGroup
import com.kitchenforce.domain.menus.MenuGroupRepository
import com.kitchenforce.domain.menus.MenuRepository
import com.kitchenforce.domain.orders.OrderMenuRepository
import com.kitchenforce.domain.orders.OrderRepository
import com.kitchenforce.domain.orders.OrderTable
import com.kitchenforce.domain.orders.OrderTableRepository
import com.kitchenforce.domain.orders.dto.OrderDto
import com.kitchenforce.domain.orders.dto.OrderMenuDto
import com.kitchenforce.domain.orders.dto.OrderTableDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class OrderServiceTest @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val orderMenuRepository: OrderMenuRepository,
    private val orderTableRepository: OrderTableRepository,
    private val orderService: OrderService
) {

    companion object {

        private val testDeliveryAddress = Delivery(
            address = "테스트시 주소동 123, 123호",
            deliveryStatus = "주문완료",
            note = "리뷰이벤트",
        )

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

        private val deliveryOrderDto = OrderDto(
            orderType = OrderType.DELIVERY,
            orderStatus = OrderStatus.WAITING,
            paymentMethod = "card",
            requirement = "맛있게 부탁드려요 :)",
            delivery = testDeliveryAddress,
            orderTableDto = null,
            orderMenuDtoList = listOf(orderMenuDto)
        )

        private val takeoutOrderDto = OrderDto(
            orderType = OrderType.TAKEOUT,
            orderStatus = OrderStatus.WAITING,
            paymentMethod = "card",
            requirement = "맛있게 부탁드려요 :)",
            delivery = testDeliveryAddress,
            orderTableDto = null,
            orderMenuDtoList = listOf(orderMenuDto)
        )

        private val eatinOrderDto = OrderDto(
            orderType = OrderType.EATIN,
            orderStatus = OrderStatus.WAITING,
            paymentMethod = "card",
            requirement = "맛있게 부탁드려요 :)",
            delivery = testDeliveryAddress,
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

            orderService.create(takeoutOrderDto)
            orderService.create(deliveryOrderDto)
            orderService.create(eatinOrderDto)
        }
    }

    @Test
    @DisplayName("1개 이상의 등록된 메뉴로 배달 또는 포장 주문을 등록할 수 있다.")
    @Transactional
    fun createOrderTest() {

        val order = orderRepository.findByIdOrNull(1L)
        val orderMenu = orderMenuRepository.findByIdOrNull(1L)

        assertTrue(order != null)
        assertThat(order?.orderType).isEqualTo(OrderType.TAKEOUT)
        assertThat(order?.orderStatus).isEqualTo(OrderStatus.WAITING)

        assertThat(orderMenu?.menu?.name).isEqualTo("메뉴1")

        // assertThat(order?.orderMenuList?.get(1)?.menu?.name).isEqualTo(orderMenuDto.menuName)
        // order에서 orderMenuList로 접근하면 왜 lateinit이 초기화가 안됬다는 에러가 날까,
    }

    @Test
    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Transactional
    fun createEatinOrderTest() {

        val order = orderRepository.findByIdOrNull(3L)
        val orderMenu = orderMenuRepository.findByIdOrNull(3L)
        val orderTable = orderTableRepository.findByIdOrNull(1L)

        assertThat(order?.orderType).isEqualTo(OrderType.EATIN)
        assertThat(order?.orderStatus).isEqualTo(OrderStatus.WAITING)
        assertThat(orderMenu?.menu?.name).isEqualTo("메뉴1")
        assertThat(orderTable?.emptiness).isEqualTo(false)
        assertThat(orderTable?.name).isEqualTo("1번")

        // assertThat(order?.orderMenuList?.get(1)?.menu?.name).isEqualTo(orderMenuDto.menuName)
        // assertThat(order?.orderTable?.name).isEqualTo("1번")
        // assertThat(order?.orderTable?.emptiness).isEqualTo(false)
    }

    @Test
    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Transactional
    fun getOrderListTest() {

        val orderDtoList = orderService.get()

        assertThat(orderDtoList[0].orderType).isEqualTo(OrderType.TAKEOUT)
        assertThat(orderDtoList[1].orderType).isEqualTo(OrderType.DELIVERY)
    }

    @Test
    @DisplayName("주문 상태를 변경할 수 있다.")
    @Transactional
    fun updateOrderStatusTest() {

        /*
        주문 상태 단계 :
        1. WAITING (대기 상태)
        2. ACCEPTED (접수 상태)
        3. SERVED (음식이 서빙된 상태)
        4. CLOSED ( 계산 후 주문이 종료가 된 상태 )
         */

        // 주문 상태 : WAITING -> ACCEPTED
        orderService.update(3L)
        val acceptedOrder = orderRepository.findByIdOrNull(3L)
        assertThat(acceptedOrder?.orderStatus).isEqualTo(OrderStatus.ACCEPTED)

        // 주문 상태 : ACCEPTED -> SERVED
        orderService.update(3L)
        val servedOrder = orderRepository.findByIdOrNull(3L)
        assertThat(servedOrder?.orderStatus).isEqualTo(OrderStatus.SERVED)

        // 주문 상태 : SERVED -> CLOSED
        orderService.update(3L)
        val closedOrder = orderRepository.findByIdOrNull(3L)
        assertThat(closedOrder?.orderStatus).isEqualTo(OrderStatus.CLOSED)

        // 주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.
        closedOrder?.let {
            val closedOrderTable: OrderTable? = orderTableRepository.findByOrderAndEmptiness(closedOrder, false)
            assertThat(closedOrderTable).isEqualTo(null)
        }
    }
}
