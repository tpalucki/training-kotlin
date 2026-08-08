package io.tpalucki.kotlin.coroutines

import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.milliseconds

fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    val job =
        scope.launch {
            GlobalScopeTest().fetchBad()
        }

    // need to observe result. Otherwise, main thread ends and kills the app
    Thread.sleep(5000)
}

/**
 * GlobalScope launches top-level coroutines tied to the entire application lifecycle, completely bypassing structured concurrency.
 *
 *  Application Lifetime ────────────────────────────────────────────────────────►
 *    └─ GlobalScope Coroutine (Runs indefinitely; no parent to cancel it)
 */

class GlobalScopeTest {
    // ❌ BAD PRACTICE: Using GlobalScope
    @OptIn(DelicateCoroutinesApi::class)
    fun fetchBad() {
        GlobalScope.launch {
            delay(3000.milliseconds) // If the screen/caller is destroyed, this STILL runs!
            println("Updated UI after screen was closed") // Memory leak / Crash!
        }

        val globalScopeWithExceptionHandler =
            GlobalScope +
                CoroutineExceptionHandler { _, exception ->
                    println("Caught exception: $exception")
                }

        globalScopeWithExceptionHandler.launch {
            delay(3000.milliseconds)
            throw RuntimeException("This will be caught by the exception handler")
        }
    }

    // ✅ GOOD PRACTICE: Tied to structured lifecycle scope
    fun fetchGood(scope: CoroutineScope) {
        scope.launch {
            delay(5000.milliseconds)
            println("Updated UI cleanly")
        }
    }
}
