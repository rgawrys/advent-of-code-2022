package utils

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = fileOf(name).readLines()

/**
 * Reads raw file content of the given input txt file as a String.
 */
fun readRawInput(name: String) = fileOf(name).readText()
private fun fileOf(name: String) = File("src/main/resources/", "$name.txt")

/**
 * Converts string to utils.md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun <T> identity(): (T) -> T = { it }
