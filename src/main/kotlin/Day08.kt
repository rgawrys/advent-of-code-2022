import utils.readInput

fun main() {
    fun part1(input: List<String>): Int = treesCountVisibleOutsideTheGrid(input)

    fun part2(input: List<String>): Int = highestScenicScore(input)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    part1(testInput).let {
        check(it == 21) { "Part 1: Incorrect result. Is `$it`, but should be `21`" }
    }
    part2(testInput).let {
        check(it == 8) { "Part 2: Incorrect result. Is `$it`, but should be `8`" }
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

private fun treesCountVisibleOutsideTheGrid(input: List<String>) =
    input
        .mapToForest()
        .toTreeLineCombinationsForEachEdge()
        .flatten()
        .filterAllVisibleTreeFromEachEdge()
        .size

private fun highestScenicScore(input: List<String>) =
    input
        .mapToForest()
        .toTreeLineCombinationsForEachEdge()
        .flatten()
        .measureViewingDistances()
        .calculateScenicScores()
        .maxOf { it.second }

private fun List<List<Tree>>.filterAllVisibleTreeFromEachEdge(): Set<Tree> = this
    .flatMap { it.filterVisibleTreesInLine() }
    .toSet()

private fun List<Tree>.filterVisibleTreesInLine(): List<Tree> = this
    .fold((-1 to emptyList<Tree>())) { acc, value ->
        val maxHeight = if (value.height > acc.first) value.height else acc.first
        val visibleTreesInLine = if (value.height > acc.first) acc.second + value else acc.second
        maxHeight to visibleTreesInLine
    }
    .second

private fun List<List<Tree>>.measureViewingDistances(): List<Pair<Tree, Int>> = this.flatMap {
    it.fold((emptyList())) { acc, value ->
        val viewingDistance = acc
            .takeLastWhile { it.first.height < value.height }
            .size
            .let { if (it == acc.size) it else it.inc() }

        acc + (value to viewingDistance)
    }
}

private fun List<Pair<Tree, Int>>.calculateScenicScores(): Set<Pair<Tree, Int>> = this.groupBy { it.first }
    .map { it.key to it.value.map { it.second }.reduce(Int::times) }
    .toSet()

private fun List<Tree>.toTreeLineCombinationsForEachEdge(): List<List<List<Tree>>> = with(this) {
    listOf(
        fromLeftEdgeLines(),
        fromRightEdgeLines(),
        fromTopEdgeLines(),
        fromBottomEdgeLines()
    )
}

private fun List<Tree>.fromLeftEdgeLines(): List<List<Tree>> = this
    .groupBy(Tree::row)
    .values
    .map { it.sortedBy(Tree::column) }

private fun List<Tree>.fromRightEdgeLines(): List<List<Tree>> = this
    .groupBy(Tree::row)
    .values
    .map { it.sortedByDescending(Tree::column) }

private fun List<Tree>.fromTopEdgeLines(): List<List<Tree>> = this
    .groupBy(Tree::column)
    .values
    .map { it.sortedBy(Tree::row) }

private fun List<Tree>.fromBottomEdgeLines(): List<List<Tree>> = this
    .groupBy(Tree::column)
    .values
    .map { it.sortedByDescending(Tree::row) }

private fun List<String>.mapToForest(): List<Tree> = this
    .flatMapIndexed { row, treeRow ->
        treeRow.mapIndexed { column, height ->
            Tree(column, row, height.toString().toInt())
        }
    }

data class Tree(val column: Int, val row: Int, val height: Int)
