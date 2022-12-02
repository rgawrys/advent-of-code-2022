import Outcome.DRAW
import Outcome.LOST
import Outcome.WON
import Shape.PAPER
import Shape.ROCK
import Shape.SCISSORS
import utils.readInput

fun main() {
    fun part1(strategyGuide: List<String>): Int =
        strategyGuide
            .toGameRounds()
            .calculateGameTotalScore()

    fun part2(input: List<String>): Int =
        input
            .toGameRoundsWithIndicatedOutcome()
            .calculateGameTotalScore2()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    part1(testInput).let {
        check(it == 15) { "Part 1: Incorrect result. Is `$it`, but should be `15`" }
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

private enum class Shape {
    ROCK, PAPER, SCISSORS
}

private enum class Outcome {
    LOST, DRAW, WON
}

private data class Round(val opponentShape: Shape, val myShape: Shape)
private data class RoundWithIndicatedOutcome(val opponentShape: Shape, val indicatedOutcome: Outcome)

private val shapeByOpponentEncryptedCodes = mapOf(
    "A" to ROCK,
    "B" to PAPER,
    "C" to SCISSORS
)
private val shapeByMyEncryptedCodes = mapOf(
    "X" to ROCK,
    "Y" to PAPER,
    "Z" to SCISSORS
)
private val indicatedOutcomeByEncryptedCodes = mapOf(
    "X" to LOST,
    "Y" to DRAW,
    "Z" to WON
)
private val scoreByShapes = mapOf(
    ROCK to 1,
    PAPER to 2,
    SCISSORS to 3
)
private val scoreByRoundOutcomes = mapOf(
    LOST to 0,
    DRAW to 3,
    WON to 6
)
private val roundOutcomeByPlays = mapOf(
    Round(ROCK, ROCK) to DRAW,
    Round(ROCK, PAPER) to WON,
    Round(ROCK, SCISSORS) to LOST,
    Round(PAPER, ROCK) to LOST,
    Round(PAPER, PAPER) to DRAW,
    Round(PAPER, SCISSORS) to WON,
    Round(SCISSORS, ROCK) to WON,
    Round(SCISSORS, PAPER) to LOST,
    Round(SCISSORS, SCISSORS) to DRAW,
)

// private val shapeToChooseByRoundsWithIndicatedOutcome =
//     roundOutcomeByPlays
//         .map { RoundWithIndicatedOutcome(it.key.opponentShape, it.value) to it.key.myShape }
//         .toMap()
private fun Round.calculateRoundScore() =
    this.myShape.toScore() + roundOutcome(this).toScore()

private fun RoundWithIndicatedOutcome.calculateRoundScore() =
    this.indicatedOutcome.toScore() + shapeToChooseForOutcome(this).toScore()

private fun List<Round>.calculateGameTotalScore(): Int = this.sumOf(Round::calculateRoundScore)
private fun List<RoundWithIndicatedOutcome>.calculateGameTotalScore2(): Int =
    this.sumOf(RoundWithIndicatedOutcome::calculateRoundScore)

private fun roundOutcome(round: Round): Outcome = roundOutcomeByPlays[round]!!
private fun shapeToChooseForOutcome(round: RoundWithIndicatedOutcome): Shape = with(round) {
    roundOutcomeByPlays
        .filterValues { it == indicatedOutcome }
        .filterKeys { it.opponentShape == opponentShape }
        .firstNotNullOf { it }
        .key
        .myShape
}

// private fun shapeToChooseForOutcome(round: RoundWithIndicatedOutcome): Shape =
//     shapeToChooseByRoundsWithIndicatedOutcome[round]!!
private fun Outcome.toScore(): Int = scoreByRoundOutcomes[this]!!
private fun Shape.toScore(): Int = scoreByShapes[this]!!
private fun List<String>.toGameRounds(): List<Round> = this.map(String::toRound)
private fun List<String>.toGameRoundsWithIndicatedOutcome(): List<RoundWithIndicatedOutcome> =
    this.map(String::toRoundWithIndicatedOutcome)

private fun String.toRound(): Round = this.split(" ").let {
    Round(it[0].toOpponentShape(), it[1].toMyShape())
}

private fun String.toRoundWithIndicatedOutcome(): RoundWithIndicatedOutcome =
    this.split(" ").let {
        RoundWithIndicatedOutcome(it[0].toOpponentShape(), it[1].toIndicatedOutcome())
    }

private fun String.toOpponentShape(): Shape = shapeByOpponentEncryptedCodes[this]!!
private fun String.toMyShape(): Shape = shapeByMyEncryptedCodes[this]!!
private fun String.toIndicatedOutcome(): Outcome = indicatedOutcomeByEncryptedCodes[this]!!
