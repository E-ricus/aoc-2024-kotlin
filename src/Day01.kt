import kotlin.math.abs

fun main() {
    fun createLists(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val firstList: MutableList<Int> = mutableListOf()
        val secondList: MutableList<Int> = mutableListOf()
        input.forEach {
            val parts = it.split(" ")
            firstList.add(parts.first().toInt())
            secondList.add(parts.last().toInt())
        }
        return Pair(firstList, secondList)
    }

    fun part1(input: List<String>): Int {
        val (firstList, secondList) = createLists(input)
        firstList.sort()
        secondList.sort()
        return firstList.zip(secondList).fold(0){ acc, pair ->
            val diff: Int = pair.first - pair.second
            acc + abs(diff)
        }
    }

    fun part2(input: List<String>): Int {
        val (firstList, secondList) = createLists(input)
        return firstList.fold(0){ acc, first ->
            val occ = secondList.count{ it == first} * first
            acc + occ
        }
    }

    val testInput = readInput("test/Day01")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("input/Day01")
    part1(input).printResult("Part 1")
    part2(input).printResult("Part 2")
}
