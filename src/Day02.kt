fun main() {
    fun levelIsSafe(level: List<Int>): Boolean {
        var isInc: Boolean? = null
        return level.zipWithNext { current, next ->
            if (isInc == null) {
                isInc = current > next
            }
            val increasing = current > next && (current - next) in 1..3
            val decreasing = current < next && (next - current) in 1..3
            if (isInc == true) increasing else decreasing
        }.all { it }
    }

    fun part1(input: List<String>): Int {
        return input.map { l ->
            val level = l.split(" ").map { it.toInt() }
            levelIsSafe(level)
        }.count { it }
    }

    fun part2(input: List<String>): Int {
        return input.map { l ->
            val level = l.split(" ").map { it.toInt() }
            var safe = false
            for(i in 0..level.lastIndex) {
                val changedLevel = level.toMutableList().apply { removeAt(i) }
                safe = levelIsSafe(changedLevel)
                if(safe) break
            }
            safe
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
