package com.kitchenforce.domain.orders.exception

import com.kitchenforce.common.exception.BaseException
import org.springframework.http.HttpStatus

class OrderException(
    errorStatus: HttpStatus,
    errorCode: Int,
    errorMessage: String,
    description: String? = null,
    cause: Throwable? = null
) : BaseException(errorStatus, errorCode, errorMessage, description, cause) {

    companion object {
        fun of(
            orderErrorCodeType: OrderErrorCodeType,
            errorMessage: String? = null,
            description: String? = null,
            cause: Throwable? = null
        ): OrderException = OrderException(
            errorStatus = orderErrorCodeType.errorStatus,
            errorCode = orderErrorCodeType.ordinal,
            errorMessage = errorMessage ?: orderErrorCodeType.errorMessage,
            description = description,
            cause = cause
        )
    }
}
