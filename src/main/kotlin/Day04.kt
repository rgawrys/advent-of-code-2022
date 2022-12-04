import utils.oneRangeFullyContainOther
import utils.rangesOverlap
import utils.readInput

fun main() {
    fun part1(input: List<String>): Int =
        countPairsThatOneRangeFullyContainOther(input)

    fun part2(input: List<String>): Int =
        countPairsThatRangesOverlap(input)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    part1(testInput).let {
        check(it == 2) { "Part 1: Incorrect result. Is `$it`, but should be `2`" }
    }
    part2(testInput).let {
        check(it == 4) { "Part 2: Incorrect result. Is `$it`, but should be `4`" }
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private fun countPairsThatOneRangeFullyContainOther(sectionAssignmentsForEachPairList: List<String>) =
    countPairsBy(sectionAssignmentsForEachPairList) { it.oneRangeFullyContainOther() }

private fun countPairsThatRangesOverlap(sectionAssignmentsForEachPairList: List<String>) =
    countPairsBy(sectionAssignmentsForEachPairList) { it.rangesOverlap() }

private fun countPairsBy(
    sectionAssignmentsForEachPairList: List<String>,
    condition: (Pair<Set<Int>, Set<Int>>) -> Boolean
) =
    sectionAssignmentsForEachPairList
        .map(String::toPair)
        .map(Pair<String, String>::toPairSections)
        .count(condition)

private fun Pair<String, String>.toPairSections(): Pair<Set<Int>, Set<Int>> =
    this.let { it.first.toSection() to it.second.toSection() }

private fun String.toSection(): Set<Int> =
    this.split("-")
        .let { it[0].toInt() to it[1].toInt() }
        .let { it.first..it.second }
        .let { it.toSet() }

private fun String.toPair(): Pair<String, String> =
    this.split(",").let { it[0] to it[1] }
