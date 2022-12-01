import utils.readRawInput

fun main() {
    fun part1(input: String): Int = maxCaloriesOfElves(input)
    fun part2(input: String): Int = sumCaloriesOfTopElves(input, 3)

    // test if implementation meets criteria from the description, like:
    val testInput = readRawInput("Day01_test")
    part1(testInput).let {
        check(it == 24000) { "Part 1: Incorrect result. Is `$it`, but should be `24000`" }
    }
    part2(testInput).let {
        check(it == 45000) { "Part 2: Incorrect result. Is `$it`, but should be `45000`" }
    }

    val input = readRawInput("Day01")
    println(part1(input))
    println(part2(input))
}

private fun sumCaloriesOfTopElves(input: String, top: Int): Int =
    input
        .toCaloriesByElves()
        .sortedByDescending { it }
        .take(top)
        .sum()

private fun maxCaloriesOfElves(input: String): Int =
    input
        .toSnacksByElves()
        .maxOf(List<Int>::sum)

private fun String.toCaloriesByElves(): List<Int> =
    this
        .split("\n\n")
        .map { it.lines().sumOf(String::toInt) }

private fun String.toSnacksByElves(): List<List<Int>> =
    this
        .split("\n\n")
        .map { it.lines().map(String::toInt) }