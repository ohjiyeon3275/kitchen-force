package com.kitchenforce.common.exception

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class BaseExceptionTest {

    @Test
    @DisplayName("BaseException Initialization Test(1)")
    fun baseExceptionFunctionTest1() {
        BaseException(
            errorStatus = HttpStatus.INTERNAL_SERVER_ERROR,
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            errorMessage = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            description = null,
            cause = null
        ).run {
            assertAll(
                { assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, this.errorStatus) },
                { assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), this.errorCode) },
                { assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase, this.errorMessage) },
                { assertNull(this.description) },
                { assertNull(this.cause) },
            )
        }
    }

    @Test
    @DisplayName("BaseException Initialization Test(2)")
    fun baseExceptionFunctionTest2() {
        BaseException(
            errorStatus = HttpStatus.INTERNAL_SERVER_ERROR,
            errorCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            errorMessage = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            description = "description",
            cause = RuntimeException("RuntimeException")
        ).run {
            assertAll(
                { assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, this.errorStatus) },
                { assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), this.errorCode) },
                { assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase, this.errorMessage) },
                { assertEquals("description", this.description) },
                { assertTrue(this.cause is RuntimeException) },
            )
        }
    }

    @Test
    @DisplayName("BaseException Initialization Test(3)")
    fun baseExceptionFunctionTest3() {
        BaseException(
            errorStatus = BaseErrorCodeType.UNKNOWN_ERROR.errorStatus,
            errorCode = BaseErrorCodeType.UNKNOWN_ERROR.errorStatus.value(),
            errorMessage = BaseErrorCodeType.UNKNOWN_ERROR.errorStatus.reasonPhrase,
            description = null,
            cause = null
        ).run {
            assertAll(
                { assertEquals(BaseErrorCodeType.UNKNOWN_ERROR.errorStatus, this.errorStatus) },
                { assertEquals(BaseErrorCodeType.UNKNOWN_ERROR.errorStatus.value(), this.errorCode) },
                { assertEquals(BaseErrorCodeType.UNKNOWN_ERROR.errorStatus.reasonPhrase, this.errorMessage) },
                { assertNull(this.description) },
                { assertNull(this.cause) },
            )
        }
    }
}
