import utils.readRawInput

fun main() {
    fun part1(input: String): Int = input.startOfPacketMarkerIndex()

    fun part2(input: String): Int = input.startOfMessageMarkerIndex()

    // test if implementation meets criteria from the description, like:
    val testInput = readRawInput("Day06_test")
    part1(testInput).let {
        check(it == 11) { "Part 1: Incorrect result. Is `$it`, but should be `11`" }
    }
    part2(testInput).let {
        check(it == 26) { "Part 2: Incorrect result. Is `$it`, but should be `26`" }
    }

    val input = readRawInput("Day06")
    println(part1(input))
    println(part2(input))
}

private const val START_OF_PACKAGE_MARKER_SIZE = 4
private const val START_OF_MESSAGE_MARKER_SIZE = 14

private fun String.startOfPacketMarkerIndex(): Int =
    this.firstMarkerAfterSequenceOfDifferentCharactersIndex(START_OF_PACKAGE_MARKER_SIZE)

private fun String.startOfMessageMarkerIndex(): Int =
    this.firstMarkerAfterSequenceOfDifferentCharactersIndex(START_OF_MESSAGE_MARKER_SIZE)

private fun String.firstMarkerAfterSequenceOfDifferentCharactersIndex(sequenceSize: Int): Int = this
    .windowedSequence(sequenceSize, 1)
    .indexOfFirst { it.toCharArray().distinct().size == sequenceSize }
    .let { it + sequenceSize }
