import utils.intersectAll
import utils.readInput
import utils.splitIntoParts

fun main() {
    fun part1(input: List<String>): Int =
        sumOfPrioritiesOfDuplicateItemTypesInBothCompartments(input)

    fun part2(input: List<String>): Int =
        sumOfPrioritiesOfItemTypesCommonByEveryThreeElvesInEachGroup(input)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    part1(testInput).let {
        check(it == 157) { "Part 1: Incorrect result. Is `$it`, but should be `157`" }
    }
    part2(testInput).let {
        check(it == 70) { "Part 2: Incorrect result. Is `$it`, but should be `70`" }
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

private fun sumOfPrioritiesOfDuplicateItemTypesInBothCompartments(rucksacks: List<String>): Int =
    rucksacks
        .map(String::toRucksackPriorityList)
        .map(List<Int>::toRucksackCompartments)
        .map(List<List<Int>>::intersectAll)
        .sumOf(Set<Int>::sum)

private fun sumOfPrioritiesOfItemTypesCommonByEveryThreeElvesInEachGroup(rucksacks: List<String>): Int =
    rucksacks
        .map(String::toRucksackPriorityList)
        .chunked(3)
        .map(List<List<Int>>::intersectAll)
        .sumOf(Set<Int>::sum)

private fun String.toRucksackPriorityList(): List<Int> =
    this.toList().map(Char::toPriority)

private fun Char.toPriority(): Int = when {
    this.isUpperCase() -> this.code - 38
    else -> this.code - 96
}

private fun List<Int>.toRucksackCompartments() = this.splitIntoParts(2)
