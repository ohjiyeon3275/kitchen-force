package com.kitchenforce.domain.products.exception

import com.kitchenforce.common.exception.ErrorCode
import org.springframework.http.HttpStatus

enum class ProductErrorCodeType(
    override val errorStatus: HttpStatus,
    override val errorMessage: String
) : ErrorCode {
    INVALID_PRICE(HttpStatus.BAD_REQUEST, "상품의 가격은 0보다 작을 수 없습니다."),
    INVALID_PRODUCT_NAME(HttpStatus.BAD_REQUEST, "상품에 비속어가 들어갈 수 없습니다.")
    ;
}
