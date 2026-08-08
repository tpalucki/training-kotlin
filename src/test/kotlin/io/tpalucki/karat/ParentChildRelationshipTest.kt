package io.tpalucki.karat

import io.tpalucki.interview.karat_hsbc.ParentChildRelationship
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse

class ParentChildRelationshipTest {
    val parentChildRelationship = ParentChildRelationship()

    @Nested
    inner class FindNodesWithZeroAndOneParents {
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

    @Nested
    inner class HasCommonAncestor {
        // Sample Graph Structure:
        //  8   4           8
        // / \ / \          |
        // 9   5   (no link) 9
        //  / \
        //  3   7
        // / \
        // 1   2
        private val parentChildPairs =
            arrayOf(
                intArrayOf(1, 3),
                intArrayOf(2, 3),
                intArrayOf(3, 5),
                intArrayOf(7, 5),
                intArrayOf(5, 4),
                intArrayOf(9, 8),
                intArrayOf(5, 8),
                intArrayOf(3, 9),
                intArrayOf(10, 11),
            )

        private val solver = ParentChildRelationship()

        @Test
        fun `should return true when nodes share direct parent`() {
            // Node 8: parent 5
            // Node 4: parent 5
            val result = solver.hasCommonAncestor(parentChildPairs, 8, 4)
            assertTrue(result)
        }

        @Test
        fun `should return true when nodes share distant ancestor`() {
            // Node 8 (parents: 5) and Node 4 (parents: 5)
            // Node 8 ancestors = {5, 4}
            // Node 4 ancestors = {5}
            val result = solver.hasCommonAncestor(parentChildPairs, 8, 4) // todo same a above
            assertTrue(result) // Both share ancestor 5
        }

        @Test
        fun `should return false when trees are completely disconnected`() {
            val result = solver.hasCommonAncestor(parentChildPairs, 8, 11)
            assertFalse(result)
        }

        @Test
        fun `should return false for nodes with no parents`() {
            val result = solver.hasCommonAncestor(parentChildPairs, 1, 2)
            assertFalse(result)
        }

        @Test
        fun `should return false when one node is ancestor of another but share no other common ancestor`() {
            val result = solver.hasCommonAncestor(parentChildPairs, 4, 5)
            assertFalse(result)
        }
    }
}
