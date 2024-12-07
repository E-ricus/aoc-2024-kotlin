import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

fun main() {
    data class CurrentPosition(var direction: Direction, var i: Int, var j: Int)

    // Returns true if escaped. false if in a loop
    fun escapeMap(
        currentPosition: CurrentPosition,
        visited: MutableSet<Pair<Int, Int>>,
        obstructions: Set<Pair<Int, Int>>,
        sizeI: Int,
        sizeJ: Int
    ): Boolean {
        val directionOffsets = mapOf(
            Direction.NORTH to (-1 to 0),
            Direction.SOUTH to (1 to 0),
            Direction.WEST to (0 to -1),
            Direction.EAST to (0 to 1)
        )

        val boundaryCheck = mapOf(
            Direction.NORTH to { pos: Pair<Int, Int> -> pos.first >= 0 },
            Direction.SOUTH to { pos: Pair<Int, Int> -> pos.first < sizeI },
            Direction.WEST to { pos: Pair<Int, Int> -> pos.second >= 0 },
            Direction.EAST to { pos: Pair<Int, Int> -> pos.second < sizeJ }
        )

        val nextDirection = mapOf(
            Direction.NORTH to Direction.EAST,
            Direction.SOUTH to Direction.WEST,
            Direction.WEST to Direction.NORTH,
            Direction.EAST to Direction.SOUTH
        )

        val visitedObstructions = mutableSetOf<Pair<Pair<Int, Int>, Direction>>()
        while (true) {
            val offset = directionOffsets[currentPosition.direction] ?: error("Invalid direction")
            val checkBoundary = boundaryCheck[currentPosition.direction] ?: error("Invalid direction")

            var newPosition = currentPosition.i to currentPosition.j

            while (newPosition !in obstructions) {
                if (!checkBoundary(newPosition)) {
                    return true // Escaped
                }
                newPosition = newPosition.first + offset.first to newPosition.second + offset.second
                if (checkBoundary(newPosition) && newPosition !in obstructions) visited.add(newPosition)
            }

            // Check for loops
            val obstructionKey = newPosition to currentPosition.direction
            if (!visitedObstructions.add(obstructionKey)) {
                return false
            }

            // Move back one step to the last valid position
            currentPosition.i = newPosition.first - offset.first
            currentPosition.j = newPosition.second - offset.second
            // Change direction
            currentPosition.direction = nextDirection[currentPosition.direction] ?: error("Invalid direction")
        }
    }


    fun part1(input: List<String>): Int {
        val sizeI = input.size
        val sizeJ = input.first().length
        var firstPosition = CurrentPosition(Direction.NORTH, 0, 0)
        val obstructions = input.flatMapIndexed { i, line ->
            line.mapIndexedNotNull { j, pos ->
                when (pos) {
                    '^' -> {
                        firstPosition = CurrentPosition(Direction.NORTH, i, j)
                        null
                    }

                    '#' -> i to j
                    else -> null
                }
            }
        }.toSet()

        val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()

        val escaped = escapeMap(firstPosition, visited, obstructions, sizeI, sizeJ)
        check(escaped)
        return visited.size
    }

    fun part2(input: List<String>): Int {
        val sizeI = input.size
        val sizeJ = input.first().length
        var firstPosition = CurrentPosition(Direction.NORTH, 0, 0)
        val obstructions = input.flatMapIndexed { i, line ->
            line.mapIndexedNotNull { j, pos ->
                when (pos) {
                    '^' -> {
                        firstPosition = CurrentPosition(Direction.NORTH, i, j)
                        null
                    }

                    '#' -> i to j
                    else -> null
                }
            }
        }.toSet()

        val loops = AtomicInteger(0)
        val normalVisited: MutableSet<Pair<Int, Int>> = mutableSetOf()
        escapeMap(firstPosition.copy(), normalVisited, obstructions, sizeI, sizeJ)

        // Coroutine improvement taken from Jetbrains livestream
        runBlocking(Dispatchers.Default) {
            for (i in input.indices) {
                for (j in input[i].indices) {
                    if (input[i][j] != '.' || (i to j) !in normalVisited) continue
                    launch {
                        val newObs = i to j
                        val currentPosition = firstPosition.copy()
                        val visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
                        val escaped = escapeMap(currentPosition, visited, obstructions + newObs, sizeI, sizeJ)
                        if (!escaped) {
                            loops.incrementAndGet()
                        }

                    }
                }
            }

        }
        return loops.toInt()
    }

    val testInput = readInputByLines("test/Day06")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInputByLines("input/Day06")
    part1(input).printResult("Part 1")
    part2(input).printResult("Part 2")
}
