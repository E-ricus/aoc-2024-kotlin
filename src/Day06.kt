enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

fun main() {
    data class CurrentPosition(var direction: Direction, var i: Int, var j: Int)

    // Returns true if escaped. false if in a loop
    fun escapeMap(
        currentPosition: CurrentPosition,
        visited: MutableSet<Pair<Int, Int>>,
        obstructions: List<Pair<Int, Int>>,
        sizeI: Int,
        sizeJ: Int,
        newObs: Pair<Int, Int>? = null,
    ): Boolean {
        val directionOffsets = mapOf(
            Direction.NORTH to (-1 to 0),
            Direction.SOUTH to (1 to 0),
            Direction.WEST to (0 to -1),
            Direction.EAST to (0 to 1)
        )

        val boundaryCheck = mapOf(
            Direction.NORTH to { pos: Pair<Int, Int> -> pos.first > 0 },
            Direction.SOUTH to { pos: Pair<Int, Int> -> pos.first < sizeI },
            Direction.WEST to { pos: Pair<Int, Int> -> pos.second > 0 },
            Direction.EAST to { pos: Pair<Int, Int> -> pos.second < sizeJ }
        )

        val nextDirection = mapOf(
            Direction.NORTH to Direction.EAST,
            Direction.SOUTH to Direction.WEST,
            Direction.WEST to Direction.NORTH,
            Direction.EAST to Direction.SOUTH
        )

        var obstructions = obstructions

        if (newObs != null) {
            obstructions = obstructions + newObs
        }
        var currentPossibleLoop: MutableList<Pair<Int, Int>>? = null
        val visitedObstructions: MutableMap<Pair<Int, Int>, Direction> = mutableMapOf()
        ins@ while (true) {
            val offset = directionOffsets[currentPosition.direction] ?: error("Invalid direction")
            val checkBoundary = boundaryCheck[currentPosition.direction] ?: error("Invalid direction")

            var newPosition = currentPosition.i to currentPosition.j

            while (newPosition !in obstructions) {
                if (!checkBoundary(newPosition)) {
                    break@ins
                }
                newPosition = newPosition.first + offset.first to newPosition.second + offset.second
                if (newPosition !in obstructions && checkBoundary(newPosition)) {
                    visited.add(newPosition)
                }
            }

            // Potential loops
            // TODO: Check what went wrong after the refactor the loops are invalid
            val possibleLoop = visitedObstructions.put(newPosition, currentPosition.direction)
            if (possibleLoop == currentPosition.direction && newObs != null) {
                return false
            }
//            when {
//                // Start possible loop
//                (newPosition == newObs && currentPossibleLoop == null) -> {
//                    currentPossibleLoop = mutableListOf(newPosition)
//                }
//
//                // Loop
//                (newPosition != newObs && currentPossibleLoop != null) -> {
//                    currentPossibleLoop.add(newPosition)
//                }
//                // End loop
//                (newPosition == newObs && currentPossibleLoop != null) -> {
//                    return false
//                }
//
//            }
//
            // Move back one step to the last valid position
            currentPosition.i = newPosition.first - offset.first
            currentPosition.j = newPosition.second - offset.second
            // Change direction
            currentPosition.direction = nextDirection[currentPosition.direction] ?: error("Invalid direction")
        }

        return true
    }


    fun part1(input: List<String>): Int {
        val sizeI = input.size
        val sizeJ = input.first().length
        var firstPosition: CurrentPosition? = null
        val obstructions = input.mapIndexed { i, line ->
            line.mapIndexed { j, pos ->
                if (pos == '^') {
                    firstPosition = CurrentPosition(Direction.NORTH, i, j)
                }
                if (pos == '#') {
                    i to j
                } else {
                    -1 to -1
                }

            }
        }.map { it.filterNot { it == (-1 to -1) } }.flatten()
        val currentPosition = firstPosition!!
        val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()

        val escaped = escapeMap(currentPosition, visited, obstructions, sizeI, sizeJ)
        check(escaped)
        return visited.size
    }

    fun part2(input: List<String>): Int {
        val sizeI = input.size
        val sizeJ = input.first().length
        var firstPosition: CurrentPosition? = null
        val obstructions = input.mapIndexed { i, line ->
            line.mapIndexed { j, pos ->
                if (pos == '^') {
                    firstPosition = CurrentPosition(Direction.NORTH, i, j)
                }
                if (pos == '#') {
                    i to j
                } else {
                    -1 to -1
                }

            }
        }.map { it.filterNot { it == (-1 to -1) } }.flatten()
        var loops = 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                // Skip obstacles and player
                if (input[i][j] != '.') continue
                val newObs = i to j
                val currentPosition = firstPosition!!
                val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
                val escaped = escapeMap(currentPosition, visited, obstructions, sizeI, sizeJ, newObs)
                if (!escaped) {
                    newObs.println()
                    loops++
                }
            }
        }
        return loops
    }

    val testInput = readInputByLines("test/Day06")
    part1(testInput).println()
    check(part1(testInput) == 41)
    part2(testInput).println()
    check(part2(testInput) == 6)

    val input = readInputByLines("input/Day06")
    part1(input).printResult("Part 1")
    part2(input).printResult("Part 2")
}
