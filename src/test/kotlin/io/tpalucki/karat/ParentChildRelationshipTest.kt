package io.tpalucki.karat

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParentChildRelationshipTest {
    val parentChildRelationship = ParentChildRelationship()

    @Test
    fun forEmptyArrayShouldReturnEmptyList() {
        val result = parentChildRelationship.findNodesWithZeroAndOneParents(emptyArray())

        assertEquals(emptyList<Int>(), result[0])
        assertEquals(emptyList<Int>(), result[1])
    }

    @Test
    fun parentsShouldAlsoBeCalculatedAsIndividuals() {
        val input =
            arrayOf(
                intArrayOf(0, 2),
                intArrayOf(0, 1),
                intArrayOf(2, 3),
            )

        val result = parentChildRelationship.findNodesWithZeroAndOneParents(input)

        // 0 parents
        assertEquals(listOf(0), result[0])
        // 1 parent
        assertEquals(listOf(1, 2, 3), result[1])
    }
}
