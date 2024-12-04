fun main() {
    val word = "XMAS"
    fun transposeMatrix(matrix: List<String>): List<String> {
        val maxLength = matrix.maxOf { it.length }
        val charMatrix = matrix.map { it.padEnd(maxLength).toList() }

        val transposed = List(maxLength) { colIndex ->
            charMatrix.map { it[colIndex] }.joinToString("")
        }

        return transposed
    }

    fun String.countSubstrings(): Int {
        var count = 0
        var index = this.indexOf(word)
        while (index != -1) {
            count++
            index = this.indexOf(word, index + 1)
        }
        return count
    }

    fun Char.validateChar(count: Int): Boolean {
        return when (count) {
            1 -> this == 'X'
            2 -> this == 'M'
            3 -> this == 'A'
            4 -> this == 'S'
            else -> false
        }
    }

    fun countDiagonal(input: List<String>): Int {
        var diagonal = 0
        for (i in input.indices) {
            val chars = input[i].toCharArray()
            for (j in chars.indices) {
                var c = chars[j]
                var count = 1
                while (c.validateChar(count)) {
                    when {
                        count == 4 -> {
                            diagonal++
                            break
                        }

                        i + count == input.size || j + count == input[i + count].length -> break
                    }
                    c = input[i + count].toCharArray()[j + count]
                    count++
                }
                c = chars[j]
                count = 1
                while (c.validateChar(count)) {
                    when {
                        count == 4 -> {
                            diagonal++
                            break
                        }

                        i + count == input.size || j - count < 0 -> break
                    }
                    c = input[i + count].toCharArray()[j - count]
                    count++
                }
                c = chars[j]
                count = 1
                while (c.validateChar(count)) {
                    when {
                        count == 4 -> {
                            diagonal++
                            break
                        }

                        i - count < 0 || j + count == input[i - count].length -> break
                    }
                    c = input[i - count].toCharArray()[j + count]
                    count++
                }
                c = chars[j]
                count = 1
                while (c.validateChar(count)) {
                    when {
                        count == 4 -> {
                            diagonal++
                            break
                        }

                        i - count < 0 || j - count < 0 -> break
                    }
                    c = input[i - count].toCharArray()[j - count]
                    count++
                }
            }
        }
        return diagonal
    }

    fun part1(input: List<String>): Int {
        val horizontal = input.sumOf {
            it.countSubstrings() + it.reversed().countSubstrings()
        }
        val transposed = transposeMatrix(input)
        val vertical = transposed.sumOf {
            it.countSubstrings() + it.reversed().countSubstrings()
        }
        val diagonal = countDiagonal(input)
        return horizontal + vertical + diagonal
    }

    fun part2(input: List<String>): Int {
        var count = 0
        for (i in input.indices) {
            val chars = input[i].toCharArray()
            for (j in chars.indices) {
                val c = chars[j]
                if (c == 'A') {
                    var leftUp = (i - 1 >= 0 && j - 1 >= 0 && input[i - 1].toCharArray()[j - 1] == 'M')
                    var leftDown = (i + 1 < input.size && j - 1 >= 0 && input[i + 1].toCharArray()[j - 1] == 'M')
                    var rightDown =
                        (i + 1 < input.size && j + 1 < input[i + 1].length && input[i + 1].toCharArray()[j + 1] == 'S')
                    var rightUp =
                        (i - 1 >= 0 && j + 1 < input[i - 1].length && input[i - 1].toCharArray()[j + 1] == 'S')
                    if (leftUp && leftDown && rightUp && rightDown) count++

                    leftUp = (i - 1 >= 0 && j - 1 >= 0 && input[i - 1].toCharArray()[j - 1] == 'S')
                    leftDown = (i + 1 < input.size && j - 1 >= 0 && input[i + 1].toCharArray()[j - 1] == 'M')
                    rightDown =
                        (i + 1 < input.size && j + 1 < input[i + 1].length && input[i + 1].toCharArray()[j + 1] == 'M')
                    rightUp =
                        (i - 1 >= 0 && j + 1 < input[i - 1].length && input[i - 1].toCharArray()[j + 1] == 'S')
                    if (leftUp && leftDown && rightUp && rightDown) count++

                    leftUp = (i - 1 >= 0 && j - 1 >= 0 && input[i - 1].toCharArray()[j - 1] == 'M')
                    leftDown = (i + 1 < input.size && j - 1 >= 0 && input[i + 1].toCharArray()[j - 1] == 'S')
                    rightDown =
                        (i + 1 < input.size && j + 1 < input[i + 1].length && input[i + 1].toCharArray()[j + 1] == 'S')
                    rightUp =
                        (i - 1 >= 0 && j + 1 < input[i - 1].length && input[i - 1].toCharArray()[j + 1] == 'M')
                    if (leftUp && leftDown && rightUp && rightDown) count++

                    leftUp = (i - 1 >= 0 && j - 1 >= 0 && input[i - 1].toCharArray()[j - 1] == 'S')
                    leftDown = (i + 1 < input.size && j - 1 >= 0 && input[i + 1].toCharArray()[j - 1] == 'S')
                    rightDown =
                        (i + 1 < input.size && j + 1 < input[i + 1].length && input[i + 1].toCharArray()[j + 1] == 'M')
                    rightUp =
                        (i - 1 >= 0 && j + 1 < input[i - 1].length && input[i - 1].toCharArray()[j + 1] == 'M')
                    if (leftUp && leftDown && rightUp && rightDown) count++
                }
            }
        }
        return count
    }

    val testInput = readInputByLines("test/Day04")
    check(part1(testInput) == 18)
    part2(testInput).println()
    check(part2(testInput) == 9)

    val input = readInputByLines("input/Day04")
    part1(input).printResult("Part 1")
    part2(input).printResult("Part 2")
}
