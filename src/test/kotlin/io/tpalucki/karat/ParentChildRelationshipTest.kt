package io.tpalucki.karat

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParentChildRelationshipTest {
    val parentChildRelationship = ParentChildRelationship()

    @Test
    fun forEmptyArrayShouldReturnEmptyList() {
        val result = parentChildRelationship.findNodesByParentCount(emptyArray())

        assertEquals(emptyList<Int>(), result.first)
        assertEquals(emptyList<Int>(), result.second)
    }
}
