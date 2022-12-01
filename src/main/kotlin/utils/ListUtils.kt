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

