package com.kitchenforce.service

import com.kitchenforce.common.exception.NotFoundException
import com.kitchenforce.domain.orders.OrderTable
import com.kitchenforce.domain.orders.OrderTableRepository
import com.kitchenforce.domain.orders.dto.OrderTableDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderTableService(
    private val orderTableRepository: OrderTableRepository,
) {

    fun get(): List<OrderTableDto> {

        val orderTableList: List<OrderTable> = orderTableRepository.findAll()

        orderTableList.map {
            OrderTableDto(
                emptiness = it.emptiness,
                tableName = it.name,
                numberOfGuests = it.numberOfGuests
            )
        }.also { return it }
    }

    fun get(tableName: String): Boolean {

        val orderTable: OrderTable? = orderTableRepository.findByNameAndEmptiness(tableName, false)

        orderTable?.let {
            return false
        } ?: return true
    }

    @Transactional
    fun update(numberOfGuests: Int, tableName: String) {
        val orderTable: OrderTable? = orderTableRepository.findByNameAndEmptiness(tableName, false)

        orderTable?.let {
            orderTable.numberOfGuests = numberOfGuests
            orderTableRepository.save(orderTable)
        } ?: throw NotFoundException("접수된 테이블이 존재하지 않습니다.")
    }
}
