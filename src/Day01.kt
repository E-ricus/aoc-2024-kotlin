import kotlin.math.abs

fun main() {
    fun createLists(input: List<String>): Pair<List<Int>, List<Int>> {
        return input.map {
            val parts = it.split(" ")
            parts.first().toInt() to parts.last().toInt()
        }.unzip()
    }

    fun part1(input: List<String>): Int {
        val (firstList, secondList) = createLists(input)
        return firstList.sorted().zip(secondList.sorted()).fold(0) { acc, pair ->
            val diff: Int = pair.first - pair.second
            acc + abs(diff)
        }
    }

    fun part2(input: List<String>): Int {
        val (firstList, secondList) = createLists(input)
        val occurrences = secondList.groupingBy { it }.eachCount()
        return firstList.sumOf { num ->
            occurrences.getOrDefault(num, 0) * num
        }
    }

    val testInput = readInputByLines("test/Day01")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputByLines("input/Day01")
    part1(input).printResult("Part 1")
    part2(input).printResult("Part 2")
}
