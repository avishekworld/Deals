package life.avishekworld.domain.util

fun String?.isNotNullAndNotEmpty() : Boolean {
    return if (this == null) false
    else !this.isNullOrEmpty()
}