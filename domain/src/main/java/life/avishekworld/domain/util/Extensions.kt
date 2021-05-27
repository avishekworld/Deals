package life.avishekworld.domain.util

fun String?.isNotNullAndNotEmpty() : Boolean {
    return if (this == null) false
    else !this.isNullOrEmpty()
}

fun String.isDigits() : Boolean {
    return this.toCharArray().fold(true) { result, char -> char.isDigit() and result }
}