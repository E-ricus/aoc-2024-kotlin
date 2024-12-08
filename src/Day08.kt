fun main() {
    fun getAntennas(input: List<String>): List<Set<Pair<Int, Int>>> {
        return input.flatMapIndexed { i, line ->
            line.mapIndexedNotNull { j, c ->
                if (c == '.') null else c to (i to j)
            }
        }.groupBy(
            keySelector = { it.first },
            valueTransform = { it.second }
        ).values.filter { it.size > 1 }
            .map { it.toSet() }
    }

    fun part1(input: List<String>): Int {
        check(input.isNotEmpty())
        val antennas = getAntennas(input)

        val verticalRange = input.indices
        val horizontalRange = input[0].indices

        val antinodes: Set<Pair<Int, Int>> = antennas.flatMap { antenna ->
            antenna.flatMap { a ->
                // Attempt of combinator
                antenna.asSequence()
                    .filter { b ->
                        b != a && // Not the same point
                                (a.first + (a.first - b.first)) in verticalRange && // Movement in I in range
                                (a.second + (a.second - b.second)) in horizontalRange // Movement in J in Range
                    }
                    .map { b ->
                        a.first + (a.first - b.first) to a.second + (a.second - b.second)
                    }
                    .toSet() // Avoid duplicate antinodes for a single antenna? Not sure if possible
            }
        }.toSet() // Ensure distinct antinodes across all antennas
        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        check(input.isNotEmpty())
        val antennas = getAntennas(input)
        val verticalRange = input.indices
        val horizontalRange = input[0].indices

        val antinodes: Set<Pair<Int, Int>> = antennas.flatMap { antenna ->
            antenna.flatMap { a ->
                // Attempt of combinator
                antenna.asSequence()
                    .filter { b ->
                        b != a && // Not the same point
                                (a.first + (a.first - b.first)) in verticalRange && // Movement in I in range
                                (a.second + (a.second - b.second)) in horizontalRange // Movement in J in Range
                    }
                    .flatMap { b ->
                        val deltaI = a.first - b.first // Movement in I
                        val deltaJ = a.second - b.second // Movement in J

                        generateSequence(a.first to a.second) { last ->
                            val next = last.first + deltaI to last.second + deltaJ // New point with the same deltas.
                            // Validate new points still in range
                            if (next.first in verticalRange && next.second in horizontalRange) next else null
                        }
                    }
                    .toSet() // Avoid duplicate antinodes for a single antenna? Not sure if possible
            }
        }.toSet() // Ensure distinct antinodes across all antennas
        // I don't get why the antennas like this are antinodes, but I won't argue
        val finalAntinodes = antennas.flatten().toSet() + antinodes
        return finalAntinodes.size
    }

    val testInput = readInputByLines("test/Day08")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInputByLines("input/Day08")
    part1(input).printResult("Part 1")
    part2(input).printResult("Part 2")
}
