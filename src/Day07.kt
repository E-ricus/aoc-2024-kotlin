fun main() {
    fun evaluate(nums: List<Long>, index: Int, currentValue: Long, target: Long, useCombinator: Boolean): Boolean {
        if (index == nums.lastIndex) {
            return currentValue == target
        }

        val nextIndex = index + 1
        val nextValue = nums[nextIndex]
        return when (useCombinator) {
            true -> {
                val concatenatedValue = (currentValue.toString() + nextValue.toString()).toLong()
                evaluate(nums, nextIndex, currentValue + nextValue, target, true) ||
                        evaluate(nums, nextIndex, currentValue * nextValue, target, true) ||
                        evaluate(nums, nextIndex, concatenatedValue, target, true)

            }

            false -> {
                evaluate(nums, nextIndex, currentValue + nextValue, target, false) ||
                        evaluate(nums, nextIndex, currentValue * nextValue, target, false)

            }
        }
    }

    fun isTarget(nums: List<Long>, target: Long, useCombinator: Boolean): Boolean {
        if (nums.isEmpty()) return false
        return evaluate(nums, 0, nums.first(), target, useCombinator)
    }

    fun part1(input: List<String>): Long {
        val equations = input.map {
            val parts = it.split(":")
            val numbers = parts[1].trim().split(" ").map { it.toLong() }
            parts[0].toLong() to numbers
        }
        return equations.sumOf { (target, numbers) ->
            if (isTarget(numbers, target, false)) target else 0
        }
    }

    fun part2(input: List<String>): Long {
        val equations = input.map {
            val parts = it.split(":")
            val numbers = parts[1].trim().split(" ").map { it.toLong() }
            parts[0].toLong() to numbers
        }
        return equations.sumOf { (target, numbers) ->
            if (isTarget(numbers, target, true)) target else 0
        }
    }

    val testInput = readInputByLines("test/Day07")
    part1(testInput).println()
    check(part1(testInput) == 3749L)
    part2(testInput).println()
    check(part2(testInput) == 11387L)

    val input = readInputByLines("input/Day07")
    part1(input).printResult("Part 1")
    part2(input).printResult("Part 2")
}
