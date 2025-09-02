package io.tpalucki.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    println("Coroutines Playground")

    runBlocking {
        // 1. Launching a coroutine
        launch {
            // CoroutineScope builder extension function
            delay(1000L)

            // 3. After delay - coroutine resumes and executes this code
            println("delayed - World from coroutine!")
        }

        // 2. execution comes back to main thread
        println("Hello from main thread!")
    }

    // 4. This is executed after runBlocking is finished
    println("this is run at the end - after runBlocking execution")
}
