package com.kitchenforce.common.exception

open class BaseExceptionTemp(
    private var baseErrorCodeType: BaseErrorCodeType
) : RuntimeException () {

    fun getErrorMessage() :String{
        return baseErrorCodeType.errorMessage
    }

}
