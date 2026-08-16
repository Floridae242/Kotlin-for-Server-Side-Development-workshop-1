package org.example

fun validateCitizenId(id: String): Boolean {
    if (id.length != 13 || !id.all(Char::isDigit)) return false

    val digits = id.map(Char::digitToInt)
    val weightedSum = digits
        .take(12)
        .mapIndexed { index, digit -> digit * (13 - index) }
        .sum()
    val expectedCheckDigit = (11 - weightedSum % 11) % 10

    return digits.last() == expectedCheckDigit
}
