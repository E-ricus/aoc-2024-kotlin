fun main() {
    fun part1(input: List<String>): Int {
        val response = input.sumOf { line ->
            val matches = "(?<=^|\\W|)mul\\(\\d{1,3},\\d{1,3}\\)".toRegex().findAll(line)
            matches.sumOf { match ->
                val numbers = match.value
                    .removePrefix("mul(")
                    .removeSuffix(")")
                    .split(",")
                    .map { it.toInt() }
                numbers[0] * numbers[1]
            }
        }
        return response
    }

    fun part2(input: List<String>): Int {
        var multiply = true
        val response = input.sumOf { line ->
            val matches = "(?<=^|\\W|)(mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don['’]?t\\(\\))".toRegex().findAll(line)
            matches.sumOf { match ->
                var summing = 0
                when {
                    match.value.startsWith("mul(") -> {
                        val numbers = match.value
                            .removePrefix("mul(")
                            .removeSuffix(")")
                            .split(",")
                            .map { it.toInt() }
                        if(multiply) {
                            summing = numbers[0] * numbers[1]
                        }

                    }
                    match.value == "do()" -> {
                        multiply = true
                    }
                    match.value == "don't()" -> {
                        multiply = false
                    }
                }
                summing
            }
        }
        return response
    }

    val testInput = readInput("test/Day03")
    part1(testInput).println()
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    val input = readInput("input/Day03")
    part1(input).printResult("Part 1")
    part2(input).printResult("Part 2")
}
