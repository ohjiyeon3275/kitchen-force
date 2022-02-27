package com.kitchenforce.domain.delivery.exception

import com.kitchenforce.common.exception.BaseException

class DeliveryException (
    deliveryErrorCodeType: DeliveryErrorCodeType,
    override val description: String? = null,
    override val cause: Throwable? = null
) : BaseException(
    deliveryErrorCodeType.errorStatus,
    deliveryErrorCodeType.errorStatus.value(),
    deliveryErrorCodeType.errorMessage,
    description,
    cause)
