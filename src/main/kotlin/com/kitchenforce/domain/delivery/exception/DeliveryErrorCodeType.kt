package com.kitchenforce.domain.delivery.exception

import com.kitchenforce.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class DeliveryErrorCodeType(
    override val errorStatus: HttpStatus,
    override val errorMessage: String
) : ErrorCode {
    NOT_MATCHED_DELIVERY_STATUS(HttpStatus.BAD_REQUEST, "잘못된 배달 상태 입니다."),
    NOT_FOUND_RIDER(HttpStatus.NOT_FOUND, "라이더를 찾을 수 없습니다"),
    NOT_FOUND_DELIVERY(HttpStatus.NOT_FOUND, "배달 주소를 찾을 수 없습니다"),
}