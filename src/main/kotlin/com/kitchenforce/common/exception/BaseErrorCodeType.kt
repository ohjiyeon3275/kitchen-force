package com.kitchenforce.common.exception

import org.springframework.http.HttpStatus

enum class BaseErrorCodeType(override val errorStatus: HttpStatus, override val errorMessage: String) : ErrorCode {
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 내부 서버 에러 입니다."),
    // 잠시 여기다 쓰겠습니다... 추후에 옮길 예정..
    NOT_MATCHED_DELIVERY_STATUS(HttpStatus.BAD_REQUEST, "잘못된 배달 상태 입니다."),
    NOT_FOUND_RIDER(HttpStatus.NOT_FOUND, "라이더를 찾을 수 없습니다"),
    NOT_FOUND_DELIVERY(HttpStatus.NOT_FOUND, "배달 주소를 찾을 수 없습니다"),

    ;
}
