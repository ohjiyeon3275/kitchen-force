package com.kitchenforce.service

import com.kitchenforce.domain.orders.OrderTableRepository
import com.kitchenforce.domain.orders.dto.OrderTableDto
import com.kitchenforce.domain.orders.exception.OrderErrorCodeType
import com.kitchenforce.domain.orders.exception.OrderException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderTableService(
    private val orderTableRepository: OrderTableRepository,
) {

    fun get(): List<OrderTableDto> = orderTableRepository.findAll().map {
        OrderTableDto(
            emptiness = it.emptiness,
            tableName = it.name,
            numberOfGuests = it.numberOfGuests
        )
    }

    fun get(tableName: String): OrderTableDto? =
        orderTableRepository.findByNameAndEmptiness(tableName, false)?.run {
            OrderTableDto(
                tableName = this.name,
                emptiness = this.emptiness,
                numberOfGuests = this.numberOfGuests
            )
        }

    @Transactional
    fun update(numberOfGuests: Int, tableName: String) {
        orderTableRepository.findByNameAndEmptiness(tableName, false)?.let {
            it.numberOfGuests = numberOfGuests
            orderTableRepository.save(it)
        } ?: throw OrderException.of(OrderErrorCodeType.ORDER_TABLE_NOT_FOUND)
    }
}
