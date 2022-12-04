package utils

fun <T> List<T>.chunkedBy(keySelector: (T) -> Boolean): List<List<T>> =
    this.chunkedBy(keySelector, identity())

fun <T, R> List<T>.chunkedBy(keySelector: (T) -> Boolean, transform: (T) -> R): List<List<R>> =
    this.fold(mutableListOf(mutableListOf<R>())) { acc, item ->
        acc.apply {
            when (keySelector(item)) {
                true -> add(mutableListOf())
                else -> last().add(transform(item))
            }
        }
    }

fun <T> List<T>.splitIntoParts(parts: Int): List<List<T>> {
    val remainder = this.size % 2
    val chunkSize = (this.size / parts) + remainder
    return this.chunked(chunkSize)
}

fun <T> List<List<T>>.intersectAll(): Set<T> =
    takeIf { this.isNotEmpty() }
        ?.fold(this.first().toSet()) { acc, item -> acc intersect item.toSet() }
        ?.toSet()
        ?: emptySet()
