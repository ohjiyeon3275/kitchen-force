package com.kitchenforce.common.exception

open class BaseExceptionTemp(
    private var baseErrorCodeType: BaseErrorCodeType
) : RuntimeException () {

    fun getErrorCodeType(errorCodeType: BaseErrorCodeType){
        this.baseErrorCodeType = baseErrorCodeType
    }

}
