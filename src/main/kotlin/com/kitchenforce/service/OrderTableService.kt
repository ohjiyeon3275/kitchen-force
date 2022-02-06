package com.kitchenforce.service

import com.kitchenforce.domain.menus.Menu
import com.kitchenforce.domain.menus.MenuRepository
import com.kitchenforce.domain.orders.*
import com.kitchenforce.domain.orders.dto.OrderDto
import com.kitchenforce.domain.orders.dto.OrderMenuDto
import com.kitchenforce.domain.orders.dto.OrderTableDto
import org.springframework.stereotype.Service

@Service
class OrderTableService (
    private val orderTableRepository: OrderTableRepository,
    private val orderRepository: OrderRepository,
    private val orderMenuRepository: OrderMenuRepository,
    private val menuRepository: MenuRepository
        ){

    fun create(dto: OrderTableDto) {
        val orderTable: OrderTable = OrderTable(
            userId = dto.userId,
            name = dto.tableName,
            emptyness = dto.emptyness,
            numberOfGuests = dto.numberOfGuests
        )
        orderTableRepository.save(orderTable)

        for (orderDto in dto.orderDtoList) {

            val order: Order = Order(
                orderType = orderDto.orderType,
                paymentPrice = 0,
                paymentMethod = orderDto.paymentMethod,
                requirement = orderDto.requirement,
                deliveryAddress = orderDto.deliveryAddress,
                orderTable = orderTable
            )
            orderRepository.save(order)

            for(orderMenuDto in orderDto.orderMenuDtoList) {

                val menu: Menu = menuRepository.findByName(orderMenuDto.menuName)
                val orderMenu: OrderMenu = OrderMenu(
                    quantity = orderMenuDto.quantity,
                    order = order,
                    menu = menu
                )
                orderMenuRepository.save(orderMenu)
            }
        }
    }

    fun orderInfo(userId: Long) : OrderTableDto {

        val orderTable: OrderTable? = orderTableRepository.findByUserId(userId)

        var orderTableDto: OrderTableDto = OrderTableDto(
            userId = orderTable!!.userId,
            emptyness = orderTable.emptyness,
            tableName = orderTable.name,
            numberOfGuests = orderTable.numberOfGuests
        )

        val orderList: MutableList<Order> = orderTable!!.orderList

        for(order in orderList) {

            var orderDto: OrderDto = OrderDto(
                orderType = order.orderType,
                paymentMethod = order.paymentMethod,
                requirement = order.requirement,
                deliveryAddress = order.deliveryAddress
            )

            val orderMenuList: MutableList<OrderMenu> = order.orderMenuList

            for (orderMenu in orderMenuList) {

                var orderMenuDto: OrderMenuDto = OrderMenuDto(
                    quantity = orderMenu.quantity,
                    menuName = orderMenu.menu.name
                )

                orderDto.orderMenuDtoList.add(orderMenuDto)
            }

            orderTableDto.orderDtoList.add(orderDto)
        }
        return orderTableDto
    }
}