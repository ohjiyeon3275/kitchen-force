package com.kitchenforce.domain.orders.exception

import com.kitchenforce.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class OrderErrorCodeType(
    override val errorStatus: HttpStatus,
    override val errorMessage: String
) : ErrorCode {

    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 주문입니다. 주문 ID를 다시 확인해주세요"),
    ORDER_TABLE_NOT_FOUND(HttpStatus.NOT_FOUND, "접수된 테이블이 존재하지 않습니다."),
    INVALID_ORDER_STATUS(HttpStatus.INTERNAL_SERVER_ERROR, "OrderStatus 상태 오류"),
    SEARCH_TABLE_ORDER_FAILED(HttpStatus.NOT_FOUND, "테이블 주문 조회 실패")
    ;
}
