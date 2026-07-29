package io.tpalucki.karat

/**
 * 📌 Problem Description
 * You are given a 2D grid where:*
 * 0 represents a board cell (walkable)
 * 1 represents a wall / obstacle (blocked)
 *
 * Given a starting coordinate (startRow, startCol), return a list of all valid adjacent cells [row, col] you can step onto. Valid steps are Up, Down, Left, Right to an adjacent 0 cell within grid boundaries.
 */
class GridState {
    // Up, Down, Left, Right
    private val directions =
        arrayOf(
            intArrayOf(-1, 0), // Up
            intArrayOf(1, 0), // Down
            intArrayOf(0, -1), // Left
            intArrayOf(0, 1), // Right
        )

    fun getValidMoves(
        grid: Array<IntArray>,
        startRow: Int,
        startCol: Int,
    ): List<IntArray> {
        val validMoves = mutableListOf<IntArray>()

        // we are in a cell on a board
        // check each direction from that cell
        // if in board (after move applied) &&
        // if no obstacle
        for (direction in directions) {
            val newRow = startRow + direction[0]
            val newCol = startCol + direction[1]
            if (newRow in grid.indices && newCol in grid[0].indices && grid[newRow][newCol] == 0) {
                validMoves.add(intArrayOf(newRow, newCol))
            }
        }
        return validMoves
    }

    /**
     * "Given a start cell and an end cell, return true if there is a valid path from start to end."
     */
    fun canReachEnd(
        grid: Array<IntArray>,
        start: IntArray,
        end: IntArray,
    ): Boolean {
        // start: [row, col]
        // end: [row,col]

        // start with first cell
        // check each direction
        // save each new cell with on obstacle to verify later
        // proceed to next cell in a queue
        val queue = mutableListOf<IntArray>()
        queue.add(start)

        val visited = mutableSetOf<Pair<Int, Int>>()
        visited.add(start[0] to start[1])

        while (queue.isNotEmpty()) {
            // check each available move from that queue
            val current = queue.removeFirst()

            if (current[0] == end[0] && current[1] == end[1]) {
                return true
            }

            val validMoves = getValidMoves(grid, current[0], current[1])

            for (move in validMoves) {
                val movePair = move[0] to move[1]
                if (!visited.contains(movePair)) {
                    visited.add(movePair)
                    queue.add(move)
                }
            }
        }

        return false
    }
}

// Runtime complexity: O(R x C)
// Space complexity: O(R x C)
