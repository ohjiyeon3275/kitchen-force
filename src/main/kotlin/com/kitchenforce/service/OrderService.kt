package com.kitchenforce.service

import com.kitchenforce.common.exception.NotFoundException
import com.kitchenforce.domain.delivery.Delivery
import com.kitchenforce.domain.delivery.DeliveryRepository
import com.kitchenforce.domain.delivery.RiderRepository
import com.kitchenforce.domain.delivery.exception.DeliveryErrorCodeType
import com.kitchenforce.domain.delivery.exception.DeliveryException
import com.kitchenforce.domain.enum.OrderStatus
import com.kitchenforce.domain.enum.OrderType
import com.kitchenforce.domain.menus.MenuRepository
import com.kitchenforce.domain.orders.*
import com.kitchenforce.domain.orders.dto.OrderDto
import org.apache.tomcat.jni.Time
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderTableRepository: OrderTableRepository,
    private val orderMenuRepository: OrderMenuRepository,
    private val menuRepository: MenuRepository,
    private val deliveryRepository: DeliveryRepository,
    private val riderRepository: RiderRepository,
) {
    @Transactional
    suspend fun create(dto: OrderDto) {

        val order = Order(
            orderStatus = OrderStatus.WAITING,
            orderType = dto.orderType,
            paymentMethod = dto.paymentMethod,
            paymentPrice = 0,
            requirement = dto.requirement,
            delivery = deliveryRepository.findByAddress(dto.delivery.address)
        )


        if(order.orderType == OrderType.DELIVERY) {

            // db갔다오면 2초씩 ++
            // 주소체크 및 저장
            deliverySave(dto.delivery, order)

            // 라이더 조회 & 호출
            riderCall(dto.delivery, 1)
        }

        orderRepository.save(order)

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

    suspend fun deliverySave(dtoDelivery: Delivery, order: Order) {

        val delivery = Delivery(
            deliveryStatus = "주문접수",
            address = dtoDelivery.address,
            note = dtoDelivery.note,
        )

        if(order.delivery == null) {
            Time.sleep(2000)
            deliveryRepository.save(delivery)
        }
    }

    suspend fun riderCall(dtoDelivery: Delivery, riderId: Long) {


        val riderOptional = riderRepository.findById(riderId)
        Time.sleep(2000)

        if(!riderOptional.isPresent){
            throw DeliveryException(DeliveryErrorCodeType.NOT_FOUND_RIDER)
        }
        val rider = riderOptional.get()

        rider.deliveries?.toMutableList()?.add(dtoDelivery)

        riderRepository.save(rider)
        Time.sleep(2000)

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
