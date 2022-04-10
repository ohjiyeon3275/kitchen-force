package com.kitchenforce.common.coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.concurrent.atomic.AtomicInteger

@SpringBootTest
@ActiveProfiles("test")
class CoroutineScopeTest {

    @Test
    fun defaultCoroutineScopeTest(): Unit = runBlocking {
        var coroutineCouter = AtomicInteger(0)
        CoroutineConfig.makeDefaultCoroutineScope().launch {
            repeat(1_000_000) {
                coroutineCouter.getAndAdd(1)
            }
        }
        delay(1_000)
        println("result : ${coroutineCouter.get()}") // 실행 환경마다 결과가 달라질 수도 있음.
    }
}
