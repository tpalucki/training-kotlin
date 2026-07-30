package io.tpalucki

import io.tpalucki.karat.DomainHitCounts
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DomainHitCountsTest {
    private val solution = DomainHitCounts()

    @Test
    fun testSingleDomain() {
        val input = arrayOf("900 google.mail.com")
        val result = solution.subdomainVisits(input)

        val resultMap = parseResultToMap(result)
        assertEquals(900, resultMap["google.mail.com"])
        assertEquals(900, resultMap["mail.com"])
        assertEquals(900, resultMap["com"])
    }

    @Test
    fun testMultipleOverlappingDomains() {
        val input =
            arrayOf(
                "900 google.mail.com",
                "50 yahoo.com",
                "1 intel.mail.com",
                "5 wiki.org",
            )
        val result = solution.subdomainVisits(input)
        val resultMap = parseResultToMap(result)

        assertEquals(900, resultMap["google.mail.com"])
        assertEquals(901, resultMap["mail.com"]) // 900 + 1
        assertEquals(951, resultMap["com"]) // 900 + 50 + 1
        assertEquals(50, resultMap["yahoo.com"])
        assertEquals(1, resultMap["intel.mail.com"])
        assertEquals(5, resultMap["wiki.org"])
        assertEquals(5, resultMap["org"])
    }

    private fun parseResultToMap(list: List<String>): Map<String, Int> =
        list.associate { entry ->
            val parts = entry.split(" ")
            parts[1] to parts[0].toInt()
        }
}
