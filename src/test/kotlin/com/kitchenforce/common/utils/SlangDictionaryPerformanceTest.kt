package com.kitchenforce.common.utils

import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.util.StopWatch

private val log = KotlinLogging.logger { }

@SpringBootTest
@ActiveProfiles("local")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@Disabled // 필요할 때 주석
class SlangDictionaryPerformanceTest(
    @Value("\${kitchen.slangwords}")
    val slangList: Array<String>,

    val slangDictionary: SlangDictionary
) {

    val testExpression = "--히어리--"
    val testSlangExpression = "----------일본당단풍그린캐스케이드-=-----------"
    var noCoroutineDict = LinearSlangDictionary(slangList)

    lateinit var stopWatch: StopWatch

    @BeforeEach
    fun setUp() {
        stopWatch = StopWatch("Test")
    }

    // 오히려 Coroutine이 더 성능이 안좋다????
    // 추측 : async context안에서 결과가 올 때까지 await를 걸어줘서 그런걸까?

    @Test
    @DisplayName("코루틴 적용 비속어 테스트 - 정상적인 언어")
    @Order(1)
    fun coroutineSlangDicTest() {
        stopWatch.start()
        val result = slangDictionary.isSlang(testExpression)
        stopWatch.stop()
        println(stopWatch.prettyPrint())
        assertFalse(result)
    }

    @Test
    @DisplayName("코루틴 미적용 비속어 테스트 - 정상적인 언어")
    @Order(2)
    fun noCoroutineSlangDicTest() {
        stopWatch.start()
        val result = noCoroutineDict.isSlang(testExpression)
        stopWatch.stop()
        println(stopWatch.prettyPrint())
        assertFalse(result)
    }

    @Test
    @DisplayName("코루틴 적용 비속어 테스트 - 중간쯤 있는 슬랭")
    @Order(3)
    fun coroutineSlangDicTest2() {
        stopWatch.start()
        val result = slangDictionary.isSlang(testSlangExpression)
        stopWatch.stop()
        println(stopWatch.prettyPrint())
        assertTrue(result)
    }

    @Test
    @DisplayName("코루틴 미적용 비속어 테스트 - 중간쯤 있는 슬랭")
    @Order(4)
    fun noCoroutineSlangDicTest2() {
        stopWatch.start()
        val result = noCoroutineDict.isSlang(testSlangExpression)
        stopWatch.stop()
        println(stopWatch.prettyPrint())
        assertTrue(result)
    }
}

class LinearSlangDictionary(
    val slangList: Array<String>
) {
    fun isSlang(expression: String): Boolean {
        return slangList.asSequence().filter {
            expression.contains(it)
        }.firstOrNull().also { log.info("Slang이 포함되어 있습니다 : $it") } != null
    }
}
