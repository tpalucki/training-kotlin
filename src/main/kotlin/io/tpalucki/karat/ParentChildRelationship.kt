package io.tpalucki.karat

/**
 * Suppose we have input data representing parent-child relationships as pairs of [parent, child].
 * Write a function that takes this input and returns two lists:
 * - All individuals who have 0 parents represented in the data.
 * - All individuals who have exactly 1 parent represented in the data.
 */
class ParentChildRelationship {
    fun findNodesByParentCount(pairs: Array<IntArray>): Pair<List<Int>, List<Int>> = Pair(emptyList(), emptyList())
}
