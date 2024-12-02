fun main() {
    fun calculateLevel(level: List<String>): Boolean {
        var isInc: Boolean? = null
        return level.mapIndexed { i, c ->
            val next = level.getOrNull(i + 1)?.toIntOrNull() ?: 0
            val current = c.toInt()
            if (isInc == null) {
                isInc = current > next
            }
            val finished = (i + 1 == level.size)
            val increasing = current > next && (current - next) in 1..3
            val decreasing = current < next && (next - current) in 1..3
            val correct = if (isInc == true) increasing else decreasing
            finished || correct
        }.all { it }
    }

    fun part1(input: List<String>): Int {
        return input.map { l ->
            val level = l.split(" ")
            calculateLevel(level)
        }.count { it }
    }

    fun part2(input: List<String>): Int {
        return input.map { l ->
            val level = l.split(" ")
            List(level.size) { ie ->
                val changedLevel = level.toMutableList()
                changedLevel.removeAt(ie)
                calculateLevel(changedLevel)
            }.any { it }
        }.count { it }
    }

    val testInput = readInput("test/Day02")
    part1(testInput).println()
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("input/Day02")
    part1(input).printResult("Part 1")
    part2(input).printResult("Part 2")
}
