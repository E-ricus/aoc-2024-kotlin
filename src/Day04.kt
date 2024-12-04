fun main() {
    val word = "XMAS"
    fun checkDiagonals(i: Int, j: Int, input: List<String>, diagonals: List<Char>): Boolean {
        val directions = listOf(
            Pair(-1, -1), // Top-left
            Pair(1, -1),  // Bottom-left
            Pair(-1, 1),  // Top-right
            Pair(1, 1)    // Bottom-right
        )

        return directions.indices.all { index ->
            val (di, dj) = directions[index]
            val x = i + di
            val y = j + dj
            x in input.indices && y in input[x].indices && input[x][y] == diagonals[index]
        }
    }

    fun part1(input: List<String>): Int {
        val directions = listOf(
            Pair(0, 1),   // Horizontal (right)
            Pair(1, 0),   // Vertical (down)
            Pair(0, -1),   // Horizontal (left)
            Pair(-1, 0),   // Vertical (up)
            Pair(1, 1),   // Diagonal (down-right)
            Pair(1, -1),   // Diagonal (down-left)
            Pair(-1, 1),   // Diagonal (up-right)
            Pair(-1, -1)   // Diagonal (uo-left)
        )

        var totalCount = 0

        for (i in input.indices) {
            for (j in input[i].indices) {
                for ((di, dj) in directions) {
                    var potential = ""
                    for (k in word.indices) {
                        val x = i + k * di
                        val y = j + k * dj
                        if (x !in input.indices || y !in input[x].indices) break
                        potential += input[x][y]
                    }
                    if (potential == word) totalCount++
                }
            }
        }

        return totalCount
    }

    fun part2(input: List<String>): Int {
        var count = 0
        val diagonals = listOf(
            listOf('M', 'M', 'S', 'S'),
            listOf('S', 'M', 'S', 'M'),
            listOf('M', 'S', 'M', 'S'),
            listOf('S', 'S', 'M', 'M'),
        )

        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == 'A') {
                    count += diagonals.count { checkDiagonals(i, j, input, it) }
                }
            }
        }
        return count
    }

    val testInput = readInputByLines("test/Day04")
    part1(testInput).println()
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInputByLines("input/Day04")
    part1(input).printResult("Part 1")
    part2(input).printResult("Part 2")
}