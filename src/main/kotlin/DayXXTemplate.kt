import utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("DayXX_test")
    part1(testInput).let {
        check(it == 1) { "Part 1: Incorrect result. Is `$it`, but should be `1`" }
    }
    // part2(testInput).let {
    //     check(it == 1) { "Part 2: Incorrect result. Is `$it`, but should be `1`" }
    // }

    val input = readInput("DayXX")
    println(part1(input))
    println(part2(input))
}
