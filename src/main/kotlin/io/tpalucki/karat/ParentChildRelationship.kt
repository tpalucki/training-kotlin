package io.tpalucki.karat

/**
 * Suppose we have input data representing parent-child relationships as pairs of [parent, child].
 * Write a function that takes this input and returns two lists:
 * - All individuals who have 0 parents represented in the data.
 * - All individuals who have exactly 1 parent represented in the data.
 */
class ParentChildRelationship {
    fun findNodesWithZeroAndOneParents(parentChildPairs: Array<IntArray>): List<List<Int>> {
        // Write your code here
        // [parent, child]
        // [0, 1]
        // [0, 2]
        // [2, 3]

        // iterate over pairs,
        // for each child, count parents
        // include parents as individuals!
        // filter entries where parent cound is 0
        // filter entries where parent count is 1

        val individualToParentCount = mutableMapOf<Int, Int>()

        for (parentChildPair in parentChildPairs) {
            val parent = parentChildPair[0]
            val child = parentChildPair[1]

            individualToParentCount.putIfAbsent(parent, 0)
            individualToParentCount[child] = individualToParentCount.getOrDefault(child, 0) + 1
        }

        val individualsWith1Parents =
            individualToParentCount
                .filter { it.value == 1 }
                .keys
                .sorted()
                .toList()
        val individualsWith0Parents =
            individualToParentCount
                .filter { it.value == 0 }
                .keys
                .sorted()
                .toList()

        return listOf(
            individualsWith0Parents,
            individualsWith1Parents,
        )
    }
}
