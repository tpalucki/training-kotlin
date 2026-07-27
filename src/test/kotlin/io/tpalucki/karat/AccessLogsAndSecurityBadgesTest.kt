package io.tpalucki.karat

import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertEquals

class AccessLogsAndSecurityBadgesTest {
    private val solver = AccessLogsAndSecurityBadges()

    @Nested
    inner class Parents {
        @Test
        fun sampleInput() {
            val input =
                arrayOf(
                    arrayOf("Paul", "enter"),
                    arrayOf("Paul", "enter"), // Mismatch: Paul entered twice without exiting
                    arrayOf("Paul", "exit"),
                    arrayOf("John", "exit"), // Mismatch: John exited without entering
                    arrayOf("Paul", "exit"), // Mismatch: Paul exited twice
                )

            val (enteredWithoutExiting, exitedWithoutEntering) = solver.findMismatchedBadges(input)

            assertEquals(listOf("Paul"), enteredWithoutExiting)
            assertEquals(listOf("John", "Paul"), exitedWithoutEntering)
        }

        @Test
        fun sampleInput1() {
            val input =
                arrayOf(
                    arrayOf("Paul", "enter"),
                    arrayOf("Paul", "enter"), // Mismatch: Paul entered twice without exiting
                )

            val (enteredWithoutExiting, exitedWithoutEntering) = solver.findMismatchedBadges(input)

            assertEquals(listOf("Paul"), enteredWithoutExiting)
            assertEquals(emptyList<String>(), exitedWithoutEntering)
        }
    }
}
