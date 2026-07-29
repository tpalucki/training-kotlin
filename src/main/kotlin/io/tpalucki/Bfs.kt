package io.tpalucki

fun main() {
}

fun bfs(
    start: Int,
    adjList: Map<Int, List<Int>>,
) {
    // start node
    // adjList node to list of nodes adjacents

// queue
// visited
    val queue = ArrayDeque<Int>()
//    val queue = mutableListOf<Int>()
    val visited = mutableSetOf<Int>()

    // starting on queue
    // while (queue not empty) {
    // take from queue
    // add adjacent to queue
    // add current ot visited
    queue.add(start)

    // tod sth with the node
    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()

        val adjacents = adjList[current] ?: emptyList()

        for (neighbor in adjacents) {
            if (visited.contains(neighbor)) continue
            queue.add(neighbor)
            visited.add(neighbor)
        }
    }
}
