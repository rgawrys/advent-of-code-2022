fun main() {
    fun part1(input: List<String>): Int =
        input
            .toCaloriesByElves()
            .maxOf(List<Int>::sum)

    fun part2(input: List<String>): Int =
        input
            .toCaloriesByElves()
            .map { it.sum() }
            .sortedByDescending { it }
            .take(3)
            .sum()
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    part1(testInput).let {
        check(it == 24000) { "Part 1: Incorrect result. Is `$it`, but should be `24000`" }
    }

    part2(testInput).let {
        check(it == 45000) { "Part 2: Incorrect result. Is `$it`, but should be `45000`" }
    }
    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

fun List<String>.toCaloriesByElves(): MutableList<MutableList<Int>> =
    this.fold(mutableListOf(mutableListOf())) { caloriesByElves, snackCalories ->
        caloriesByElves.apply {
            when (snackCalories.isBlank()) {
                true -> add(mutableListOf())
                else -> last().add(snackCalories.toInt())
            }
        }
    }
