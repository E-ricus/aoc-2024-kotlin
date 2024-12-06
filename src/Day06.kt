enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

fun main() {
    data class CurrentPosition(var direction: Direction, var i: Int, var j: Int)

    fun escapeMap(
        currentPosition: CurrentPosition,
        visited: MutableSet<Pair<Int, Int>>,
        obstructions: List<Pair<Int, Int>>,
        sizeI: Int,
        sizeJ: Int
    ): Int {
        var isInside = true
        while (isInside) {
            when (currentPosition.direction) {
                Direction.NORTH -> {
                    var newPosition = currentPosition.i to currentPosition.j
                    visited.add(newPosition)
                    while (newPosition !in obstructions) {
                        if (newPosition.first == 0) {
                            isInside = false
                            break
                        }
                        newPosition = newPosition.first - 1 to newPosition.second
                        if (newPosition !in obstructions) {
                            visited.add(newPosition)
                        }
                    }
                    currentPosition.i = newPosition.first + 1
                    currentPosition.direction = Direction.EAST
                }

                Direction.SOUTH -> {
                    var newPosition = currentPosition.i to currentPosition.j
                    visited.add(newPosition)
                    while (newPosition !in obstructions) {
                        if (newPosition.first == sizeI) {
                            isInside = false
                            break
                        }
                        newPosition = newPosition.first + 1 to newPosition.second
                        if (newPosition !in obstructions) {
                            visited.add(newPosition)
                        }
                    }
                    currentPosition.i = newPosition.first - 1
                    currentPosition.direction = Direction.WEST
                }

                Direction.WEST -> {
                    var newPosition = currentPosition.i to currentPosition.j
                    visited.add(newPosition)
                    while (newPosition !in obstructions) {
                        if (newPosition.second == 0) {
                            isInside = false
                            break
                        }
                        newPosition = newPosition.first to newPosition.second - 1
                        if (newPosition !in obstructions) {
                            visited.add(newPosition)
                        }
                    }
                    currentPosition.j = newPosition.second + 1
                    currentPosition.direction = Direction.NORTH
                }

                Direction.EAST -> {
                    var newPosition = currentPosition.i to currentPosition.j
                    visited.add(newPosition)
                    while (newPosition !in obstructions) {
                        if (newPosition.second == sizeJ) {
                            isInside = false
                            break
                        }
                        newPosition = newPosition.first to newPosition.second + 1
                        if (newPosition !in obstructions) {
                            visited.add(newPosition)
                        }
                    }
                    currentPosition.j = newPosition.second - 1
                    currentPosition.direction = Direction.SOUTH
                }
            }
        }
        return visited.size - 1
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

        return escapeMap(currentPosition, visited, obstructions, sizeI, sizeJ)
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInputByLines("test/Day06")
    part1(testInput).println()
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInputByLines("input/Day06")
    part1(input).printResult("Part 1")
    part2(input).printResult("Part 2")
}
