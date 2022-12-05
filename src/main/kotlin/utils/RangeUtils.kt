package utils

infix fun IntRange.overlap(otherRange: IntRange): Boolean =
    (this intersect otherRange).isNotEmpty()
