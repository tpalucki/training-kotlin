package io.tpalucki.interview.karat_hsbc

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

    /**
     * Determines whether two nodes share at least one common ancestor.
     *
     * @param parentChildPairs 2D array of [parent, child] integer pairs
     * @param node1 First target node ID
     * @param node2 Second target node ID
     * @return true if node1 and node2 share a common ancestor, false otherwise
     */
    fun hasCommonAncestor(
        parentChildPairs: Array<IntArray>,
        node1: Int,
        node2: Int,
    ): Boolean {
        val childToParents = mutableMapOf<Int, MutableList<Int>>()

        for (parentChildPair in parentChildPairs) {
            val parent = parentChildPair[0]
            val child = parentChildPair[1]
            childToParents.computeIfAbsent(child) { mutableListOf() }.add(parent)
        }

        // first map of child to parent
        // then iterate over node -> parent + add to list of node ancestors
        fun getAncestors(node: Int): Set<Int> {
            // node might have multiple parent
            // each parent might have multiple parents
            // so we have to flatten it

            // check node parents
            // add each to collection
            // tak from collection, add to output, lookup it's parents and addd to stack
            val queue = ArrayDeque<Int>()
            queue.add(node)
            val ancestors = mutableSetOf<Int>()

            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()

                val currentParents = childToParents[current] ?: emptyList()

                ancestors.addAll(currentParents)

                for (currentParent in currentParents) {
                    if (!ancestors.contains(currentParent)) {
                        queue.add(currentParent)
                    }
                }
            }
            return ancestors
        }

        val node1Ancestors = getAncestors(node1)
        val node2Ancestors = getAncestors(node2)
        return node1Ancestors
            .intersect(node2Ancestors)
            .isNotEmpty()
    }
}
