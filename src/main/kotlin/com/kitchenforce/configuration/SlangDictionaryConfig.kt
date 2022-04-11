package com.kitchenforce.configuration

import com.kitchenforce.common.utils.SlangDictionary
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SlangDictionaryConfig(
    @Value("\${kitchen.slangwords}")
    val slangList: Array<String>

) {
    @Bean
    fun slangDictionary(): SlangDictionary = SlangDictionary(slangList)
}
