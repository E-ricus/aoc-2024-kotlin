// Operators as data object: Taken from Jetbrains stream
sealed interface Operator : (Long, Long) -> Long
data object Add : Operator {
    override fun invoke(a: Long, b: Long): Long = a + b
}

data object Multiply : Operator {
    override fun invoke(a: Long, b: Long): Long = a * b
}

data object Concat : Operator {
    override fun invoke(a: Long, b: Long): Long = "$a$b".toLong()
}

fun main() {
    fun evaluate(nums: List<Long>, index: Int, currentValue: Long, target: Long, operators: List<Operator>): Boolean {
        if (index == nums.lastIndex) return currentValue == target
        if (currentValue > target) return false

        val nextIndex = index + 1
        val nextValue = nums[nextIndex]
        return operators.any { op ->
            evaluate(nums, nextIndex, op(currentValue, nextValue), target, operators)
        }
    }

    fun isTarget(nums: List<Long>, target: Long, useConcat: Boolean): Boolean {
        if (nums.isEmpty()) return false
        val operators = if (useConcat) listOf(Add, Multiply, Concat) else listOf(Add, Multiply)
        return evaluate(nums, 0, nums.first(), target, operators)
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
