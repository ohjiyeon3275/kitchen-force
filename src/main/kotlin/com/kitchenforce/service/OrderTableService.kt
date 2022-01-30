package com.kitchenforce.service

import com.kitchenforce.domain.menus.Menu
import com.kitchenforce.domain.menus.MenuRepository
import com.kitchenforce.domain.orders.*
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

        for (orders in dto.orderList) {

            val order: Order = Order(
                orderType = orders.orderType,
                paymentPrice = 0,
                paymentMethod = orders.paymentMethod,
                requirement = orders.requirement,
                deliveryAddress = orders.deliveryAddress,
                orderTable = orderTable
            )
            //entity가 데이터 클래스가 아니어도 접근 및 값 수정 가능한것인가 ?
            orderTable.orderList.add(order)

            for(orderMenus in orders.orderMenuList) {

                val menu: Menu = menuRepository.findByName(orderMenus.menuName)
                val orderMenu: OrderMenu = OrderMenu(
                    quantity = orderMenus.quantity,
                    order = order,
                    menu = menu
                )
                order.orderMenuList.add(orderMenu)

                orderMenuRepository.save(orderMenu)
            }
            orderRepository.save(order)
        }
        orderTableRepository.save(orderTable)
    }
}