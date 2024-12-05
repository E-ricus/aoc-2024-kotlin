fun main() {
    fun isCorrect(
        updates: List<Int>,
        rules: List<Pair<Int, Int>>,
    ): Boolean {
        for ((left, right) in rules) {
            val indexLeft = updates.indexOf(left)
            if (indexLeft >= 0) {
                val indexRight = updates.indexOf(right)
                if (indexRight == -1) continue
                if (indexLeft > indexRight) {
                    return false
                }
            }
        }
        return true
    }

    fun firstErrorIndices(update: List<Int>, rules: Map<Int, List<Int>>): Pair<Int, Int>? {
        for (i in update.indices) {
            val x = update[i]
            val before = rules[x] ?: continue
            for (j in i + 1..update.lastIndex) {
                if (update[j] in before) {
                    return i to j
                }
            }
        }
        return null
    }

    fun part1(input: String): Int {
        val parts = input.split("\n\n")
        check(parts.size == 2)
        val rules = parts[0].lines().map {
            val vals = it.split("|")
            check(vals.size == 2)
            vals[0].toInt() to vals[1].toInt()
        }
        val updatesList = parts[1].lines().map {
            it.split(",").map { it.toInt() }
        }

        val response = updatesList.sumOf { updates ->
            val correct = isCorrect(updates, rules)
            val middleIndex = updates.size / 2
            val middleElement = updates[middleIndex]
            if (correct) middleElement else 0
        }

        return response
    }

    fun MutableList<Int>.swapListElements(index1: Int, index2: Int) {
        this[index1] = this[index2].also { this[index2] = this[index1] }
    }


    fun part2(input: String): Int {
        val parts = input.split("\n\n")
        check(parts.size == 2)
        val rulesPair = parts[0].lines().map {
            val vals = it.split("|")
            check(vals.size == 2)
            vals[0].toInt() to vals[1].toInt()
        }
        val rulesMap: Map<Int, List<Int>> = rulesPair.groupBy(
            keySelector = { it.first },
            valueTransform = { it.second }
        )
        val updatesList = parts[1].lines().map {
            it.split(",").map { it.toInt() }
        }

        val response = updatesList.filterNot { isCorrect(it, rulesPair) }.sumOf { up ->
            val updates = up.toMutableList()
            var errorIndices = firstErrorIndices(updates, rulesMap)
            while (errorIndices != null) {
                updates.swapListElements(errorIndices.first, errorIndices.second)
                errorIndices = firstErrorIndices(updates, rulesMap)
            }

            val middleIndex = updates.size / 2
            updates[middleIndex]
        }

        return response
    }

    val testInput = readInputFull("test/Day05")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInputFull("input/Day05")
    part1(input).printResult("Part 1")
    part2(input).printResult("Part 2")
}
