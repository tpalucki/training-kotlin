package io.tpalucki.coroutines

import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

/**
 * Standard Scope (Fail-All)          Supervisor Scope (Isolated)
 *
 *         Parent                              Parent
 *        /      \                            /      \
 *    Child A   Child B                   Child A   Child B
 *      💥         ❌                        💥         ✅
 * (A fails -> B is cancelled)       (A fails -> B keeps running)
 */

fun main() {
    // Non-blocking bridge: launches on background thread pool
    val scope = CoroutineScope(Dispatchers.Default)
    val job =
        scope.launch {
            SupervisorScopeTest().trigger()
        }

    println("Main thread still working...")
    // Keep process alive briefly so background task can finish (for CLI/testing)
//    Thread.sleep(1000)
    // or join job to current thread
    runBlocking { job.join() }
}

class SupervisorScopeTest {
    suspend fun trigger() =
        // supervisorScope creates an isolated failure domain
        supervisorScope {
            val userId = "user-${Random.nextInt()}"

//            supervisorScope {
//                launch { throw RuntimeException() } ───► 1. Fails
//                2. Does NOT cancel sibling coroutines ✅
//                3. Reaches top of scope boundary...
//                4. Uncaught -> Sent to CoroutineExceptionHandler 💥
//            }
            launch {
                println("Starting supervisorScope for $userId")
                delay(100.milliseconds)
                throw RuntimeException("Simulated failure of child 1 in supervisorScope for $userId")
            }

            launch {
                println("Child 2 starting...")
                delay(500.milliseconds)
                println("Child 2 finished successfully! ✅")
            }

            // exception handler for throwables thrown from coroutine
            val handler =
                CoroutineExceptionHandler { _, throwable ->
                    println("[ERROR] Caught exception: $throwable")
                }
            launch(handler) {
                println("Child 3 starting...")
                delay(200.milliseconds)
                throw RuntimeException("Simulated failure of child 3 in supervisorScope for $userId")
            }
        }
}
