fun main() {
    fun part1(input: List<String>): Int {
        return input.map { l ->
            val level = l.split(" ")
            var isInc: Boolean? = null
            level.mapIndexed{ i, c ->
                val n = level.getOrNull(i + 1) ?: "0"
                val next = n.toInt()
                val current = c.toInt()
                if(isInc == null) {
                    isInc = current > next
                }
                val finished = (i+1 == level.size)
                val increasing = current > next && listOf(1, 2, 3).contains(current - next)
                val decreasing = current < next && listOf(1, 2, 3).contains(next - current)
                val correct = if(isInc == true) increasing else decreasing
                finished || correct
            }.all { it }
        }.count { it }
    }

    fun part2(input: List<String>): Int {
        return input.map { l ->
            val level = l.split(" ")
            List(level.size) { ie ->
                val changedLevel = level.toMutableList()
                changedLevel.removeAt(ie)
                var isInc: Boolean? = null
                changedLevel.mapIndexed{ i, c ->
                    val n = changedLevel.getOrNull(i + 1) ?: "0"
                    val next = n.toInt()
                    val current = c.toInt()
                    if(isInc == null) {
                        isInc = current > next
                    }
                    val finished = (i+1 == changedLevel.size)
                    val increasing = current > next && listOf(1, 2, 3).contains(current - next)
                    val decreasing = current < next && listOf(1, 2, 3).contains(next - current)
                    val correct = if(isInc == true) increasing else decreasing
                    finished || correct
                }.all { it }
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
