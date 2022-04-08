package com.kitchenforce.common.utils

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class SlangDictionaryTest {

    @Test
    @DisplayName("비속어 사전의 기능 테스트")
    fun slangDictionaryTest() {
        val result = SlangDictionary.isSlang("정상적입니다.")
        assertFalse(result)
    }

    @Test
    @DisplayName("비속어 필터링에 걸리는 예제 테스트")
    fun slangDictionayCatchTest() {
        val result = SlangDictionary.isSlang("---청둥오리버들---")
        assertTrue(result)
    }
}
