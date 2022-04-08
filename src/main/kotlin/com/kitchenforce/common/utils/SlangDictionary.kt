package com.kitchenforce.common.utils

import com.kitchenforce.common.coroutine.CoroutineConfig
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val log = KotlinLogging.logger { }
// TODO : 비속어 사전 역할을 하는 정적 클래스 입니다.
// 이걸 DB나 다른 저장소를 통해 관리하겠다고 하면 해당 싱글턴 구현체는 적절하지 않을 수 있습니다.
object SlangDictionary {

    lateinit var slangList: Array<String>

    fun isSlang(expression: String): Boolean = runBlocking {
        // Coroutine 내부에서 처리된 내용의 결과를 받아야 되기 때문에 async 함수 호출
        CoroutineConfig.makeDefaultCoroutineScope().async {
            repeat(slangList.size) { index ->
                if (expression.contains(slangList[index])) {
                    log.info("Slang이 포함되어 있습니다 : ${slangList[index]}")
                    return@async true
                }
            }
            return@async false
        }.await()
    }
}

@Configuration
class SlangDictionaryConfiguration(
    @Value("\${kitchen.slangwords}")
    val slangList: Array<String>
) {

    @Bean
    fun slangDictionary(): SlangDictionary {
        return SlangDictionary.also {
            it.slangList = slangList
        }
    }
}
