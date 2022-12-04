package utils

fun <T> Pair<Set<T>, Set<T>>.oneRangeFullyContainOther(): Boolean =
    (this.first intersect this.second).size in setOf(this.first.size, this.second.size)

fun <T> Pair<Set<T>, Set<T>>.rangesOverlap(): Boolean =
    (this.first intersect this.second).isNotEmpty()
