package com.kitchenforce.service

import com.kitchenforce.domain.delivery.DeliveryAddressRepository
import com.kitchenforce.domain.menus.MenuRepository
import com.kitchenforce.domain.orders.OrderMenuRepository
import com.kitchenforce.domain.orders.OrderRepository
import com.kitchenforce.domain.orders.OrderTable
import com.kitchenforce.domain.orders.OrderTableRepository
import com.kitchenforce.domain.orders.dto.OrderTableDto
import org.springframework.stereotype.Service

@Service
class OrderTableService(
    private val orderTableRepository: OrderTableRepository,
    private val orderRepository: OrderRepository,
    private val orderMenuRepository: OrderMenuRepository,
    private val menuRepository: MenuRepository,
    private val deliveryAddressRepository: DeliveryAddressRepository
) {

    fun get(): List<OrderTableDto> {

        val orderTableList: List<OrderTable> = orderTableRepository.findAll()

        orderTableList.map {
            OrderTableDto(
                emptyness = it.emptyness,
                tableName = it.name,
                numberOfGuests = it.numberOfGuests
            )
        }.also { return it }
    }
}
