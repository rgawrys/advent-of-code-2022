import utils.readInput

fun main() {
    fun part1(input: List<String>): String = input
        .executeRearrangementProcedure(canPickUpMultipleCrates = false)
        .lastValuesAsString()

    fun part2(input: List<String>): String = input
        .executeRearrangementProcedure(canPickUpMultipleCrates = true)
        .lastValuesAsString()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    part1(testInput).let {
        check(it == "CMZ") { "Part 1: Incorrect result. Is `$it`, but should be `CMZ`" }
    }
    part2(testInput).let {
        check(it == "MCD") { "Part 2: Incorrect result. Is `$it`, but should be `MCD`" }
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private val procedureInputLineRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()

data class Procedure(val count: Int, val from: Int, val to: Int)

private fun List<String>.executeRearrangementProcedure(canPickUpMultipleCrates: Boolean): Map<Int, List<Char>> {
    val procedures = this.extractRearrangementProcedureList()
    val startingCratesStocks = this
        .extractStartingCratesStocks()
        .mapValues { it.value.toMutableList() }
        .toMutableMap()

    return procedures.executeRearrangementProcedure(startingCratesStocks, canPickUpMultipleCrates)
}

private fun List<Procedure>.executeRearrangementProcedure(
    startingCratesStocks: Map<Int, List<Char>>,
    canPickUpMultipleCrates: Boolean
): Map<Int, List<Char>> {
    val stacks = startingCratesStocks.mapValues { it.value.toMutableList() }.toMutableMap()

    return this.forEach { procedure ->
        stacks[procedure.from]!!.takeLast(procedure.count)
            .let { if (canPickUpMultipleCrates) it else it.reversed() }
            .also { stacks[procedure.to]!!.addAll(it) }

        stacks[procedure.from]!!.dropLast(procedure.count).toMutableList().also {
            stacks[procedure.from] = it
        }
    }.run { stacks }
}

private fun Map<Int, List<Char>>.lastValuesAsString(): String = this.lastValues().joinToString("")
private fun Map<Int, List<Char>>.lastValues(): List<Char> = this.map { it.value.last() }

private fun List<String>.extractStartingCratesStocks(): Map<Int, List<Char>> =
    this.takeWhile { it.isNotEmpty() }
        .dropLast(1)
        .map { it.toCharArray().filterIndexed { index, _ -> (index - 1) % 4 == 0 } }
        .flatMap { it.mapIndexed { index, value -> index.inc() to takeIf { value.isLetter() }?.let { value } } }
        .groupBy({ it.first }, { it.second })
        .mapValues { it.value.filterNotNull().reversed() }

private fun List<String>.extractRearrangementProcedureList(): List<Procedure> =
    this.takeLastWhile { it.startsWith("move") }
        .map { it.toRearrangementProcedure() }

private fun String.toRearrangementProcedure(): Procedure =
    procedureInputLineRegex.matchEntire(this)!!.destructured
        .let { (count, from, to) -> Procedure(count.toInt(), from.toInt(), to.toInt()) }
