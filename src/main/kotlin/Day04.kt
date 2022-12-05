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

private val inputLineRegex = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()

private fun countPairsThatOneRangeFullyContainOther(sectionAssignmentsForEachPairList: List<String>) =
    countPairsBy(sectionAssignmentsForEachPairList) { it.oneRangeFullyContainOther() }

private fun countPairsThatRangesOverlap(sectionAssignmentsForEachPairList: List<String>) =
    countPairsBy(sectionAssignmentsForEachPairList) { it.rangesOverlap() }

private fun countPairsBy(
    sectionAssignmentsForEachPairList: List<String>,
    condition: (Pair<IntRange, IntRange>) -> Boolean
) =
    sectionAssignmentsForEachPairList
        .map(String::toPairSection)
        .count(condition)

private fun String.toPairSection(): Pair<IntRange, IntRange> =
    inputLineRegex.matchEntire(this)!!.destructured
        .let { (startX, endX, startY, endY) -> startX.toInt()..endX.toInt() to startY.toInt()..endY.toInt() }

// parsing input using split function
// private fun Pair<String, String>.toPairSections(): Pair<IntRange, IntRange> =
//     this.let { it.first.toSection() to it.second.toSection() }
//
// private fun String.toSection(): IntRange =
//     this.split("-")
//         .let { it[0].toInt() to it[1].toInt() }
//         .let { it.first..it.second }
//
// private fun String.toPair(): Pair<String, String> =
//     this.split(",").let { it[0] to it[1] }
