package com.kitchenforce.service

import com.kitchenforce.common.exception.NotFoundException
import com.kitchenforce.domain.delivery.Delivery
import com.kitchenforce.domain.delivery.DeliveryRepository
import com.kitchenforce.domain.enum.OrderStatus
import com.kitchenforce.domain.enum.OrderType
import com.kitchenforce.domain.menus.MenuRepository
import com.kitchenforce.domain.orders.*
import com.kitchenforce.domain.orders.dto.OrderDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderTableRepository: OrderTableRepository,
    private val orderMenuRepository: OrderMenuRepository,
    private val menuRepository: MenuRepository,
    private val deliveryAddressRepository: DeliveryRepository
) {
    @Transactional
    fun create(dto: OrderDto) {

        val order = Order(
            orderStatus = OrderStatus.WAITING,
            orderType = dto.orderType,
            paymentMethod = dto.paymentMethod,
            paymentPrice = 0,
            requirement = dto.requirement,
            delivery = deliveryAddressRepository.findByAddress(dto.delivery.address)
        )

        val testDeliveryAddress = Delivery(
            deliveryStatus = "주문완료",
            address = "주소",
            note = "리뷰이벤트",
        )

        val saved = orderRepository.save(order)
        println("저장되었습니다.")
        println(saved.id)
        println(saved.orderType)

        if (dto.orderType == OrderType.EATIN) {
            val orderTable = OrderTable(
                name = dto.orderTableDto?.tableName ?: "테이블 지정 안됨.",
                emptiness = dto.orderTableDto?.emptiness ?: true,
                numberOfGuests = dto.orderTableDto?.numberOfGuests ?: 0,
                order = order
            )
            orderTableRepository.save(orderTable)
        }

        dto.orderMenuDtoList.map {

            if (menuRepository.findByName(it.menuName)?.isHidden == true) throw NotFoundException("숨겨진 메뉴입니다.")

            OrderMenu(
                quantity = it.quantity,
                order = order,
                menu = menuRepository.findByName(it.menuName) ?: throw NotFoundException("메뉴가 존재하지 않습니다.")
            )
        }.also { orderMenuRepository.saveAll(it) }
    }

    fun get(): List<OrderDto> {

        val orderList: List<Order> = orderRepository.findAll()

        orderList.map {
            OrderDto(
                orderType = it.orderType,
                orderStatus = it.orderStatus,
                paymentMethod = it.paymentMethod,
                requirement = it.requirement,
                delivery = it.delivery ?: Delivery(-1, "", "", ""),
                orderTableDto = null,
                orderMenuDtoList = ArrayList()
            )
        }.also { return it }
    }

    @Transactional
    fun update(id: Long) {

        val order: Order? = orderRepository.findByIdOrNull(id)

        order?.let {
            val orderTable: OrderTable? = orderTableRepository.findByOrderAndEmptiness(it, false)

            when (it.orderStatus) {
                OrderStatus.WAITING -> it.orderStatus = OrderStatus.ACCEPTED
                OrderStatus.ACCEPTED -> it.orderStatus = OrderStatus.SERVED
                OrderStatus.SERVED -> {
                    it.orderStatus = OrderStatus.CLOSED
                    orderTable?.emptiness = true
                }
            }
            orderTable?.let { orderTableRepository.save(orderTable) }
            orderRepository.save(order)
        } ?: throw NotFoundException("주문이 존재하지 않습니다.")
    }
}
