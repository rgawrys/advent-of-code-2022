package utils

fun Pair<IntRange, IntRange>.oneRangeFullyContainOther(): Boolean =
    (this.first intersect this.second).count() in setOf(this.first.count(), this.second.count())

fun Pair<IntRange, IntRange>.rangesOverlap(): Boolean =
    this.first overlap this.second
