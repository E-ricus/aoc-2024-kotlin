fun main() {
    fun part1(input: String): Int {
        val matches = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex().findAll(input)
        val response = matches.sumOf { match ->
            val (first, second) = match.destructured
            first.toInt() * second.toInt()
        }
        return response
    }

    fun part2(input: String): Int {
        var multiply = true
        val matches = """mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""".toRegex().findAll(input)
        val response = matches.sumOf { match ->
            var summing = 0
            when (match.value) {
                "do()" -> multiply = true
                "don't()" -> multiply = false
                else -> {
                    val (first, second) = match.destructured
                    if (multiply) {
                        summing = first.toInt() * second.toInt()
                    }
                }
            }
            summing
        }
        return response
    }

    val testInput = readInputFull("test/Day03")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    val input = readInputFull("input/Day03")
    part1(input).printResult("Part 1")
    part2(input).printResult("Part 2")
}
