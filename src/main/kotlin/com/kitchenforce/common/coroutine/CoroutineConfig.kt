package com.kitchenforce.common.coroutine

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import mu.KotlinLogging

private val log = KotlinLogging.logger {}
object CoroutineConfig {

// https://kotlinlang.org/docs/exception-handling.html#coroutineexceptionhandler
    val handler = CoroutineExceptionHandler { _, exception ->
        log.error("CoroutineException : $exception")
    }

    fun makeDefaultCoroutineScope() = CoroutineScope(Dispatchers.Default + handler)
}
