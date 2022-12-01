import utils.chunkedBy
import utils.readInput

fun main() {
    fun part1(input: List<String>): Int = maxCaloriesOfElves(input)
    fun part2(input: List<String>): Int = sumCaloriesOfTopElves(input, 3)

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

private fun sumCaloriesOfTopElves(input: List<String>, top: Int): Int =
    input
        .toCaloriesByElves()
        .sortedByDescending { it }
        .take(top)
        .sum()

private fun maxCaloriesOfElves(input: List<String>): Int =
    input
        .toSnacksByElves()
        .maxOf(List<Int>::sum)

private fun List<String>.toCaloriesByElves(): List<Int> =
    this.toSnacksByElves().map { it.sum() }

private fun List<String>.toSnacksByElves(): List<List<Int>> =
    this.chunkedBy(String::isBlank) { it.toInt() }
