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

        val orderTableList: List<OrderTable>? = orderTableRepository.findAll()

        val orderTableDtoListNothing: MutableList<OrderTableDto> = ArrayList()
        val orderTableDtoList: MutableList<OrderTableDto> = ArrayList()

        orderTableList?.let {

            for (orderTable in orderTableList) {
                val orderTableDto: OrderTableDto = OrderTableDto(
                    emptyness = orderTable.emptyness,
                    tableName = orderTable.name,
                    numberOfGuests = orderTable.numberOfGuests
                )
                orderTableDtoList.add(orderTableDto)
            }
            return orderTableDtoList
        } ?: return orderTableDtoListNothing
    }
}
