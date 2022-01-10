package com.kitchenforce.domain.products.exception

import com.kitchenforce.common.exception.BaseException
import org.springframework.http.HttpStatus

class ProductException(
    override val errorStatus: HttpStatus,
    override val errorCode: Int,
    override val errorMessage: String,
    override val description: String? = null,
    override val cause: Throwable? = null
) : BaseException(errorStatus, errorCode, errorMessage, description, cause) {

    companion object {
        fun of(
            productErrorCodeType: ProductErrorCodeType,
            description: String? = null,
            cause: Throwable? = null
        ): ProductException = ProductException(
            errorStatus = productErrorCodeType.errorStatus,
            errorCode = productErrorCodeType.errorStatus.value(),
            errorMessage = productErrorCodeType.errorMessage,
            description = description,
            cause = cause
        )
    }
}
