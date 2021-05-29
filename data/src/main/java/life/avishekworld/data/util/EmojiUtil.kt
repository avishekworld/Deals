package life.avishekworld.data.util

interface EmojiUtil {
    fun stringToEmojiUnicode(text : String?) : String
}

class EmojiUtilImpl : EmojiUtil {
    //todo add more emoji unicode - utf-16 codes
    private val emojiUnicodeMap = mapOf(
            ":)" to "\uD83D\uDE0A",
            ":D" to "\uD83D\uDE04",
            ":wave" to "\uD83D\uDC4B"
    )
    override fun stringToEmojiUnicode(text: String?): String {
        return text?.let {
            var convertedString = it
            emojiUnicodeMap.forEach { entry ->
                convertedString = convertedString.replace(entry.key, entry.value)
            }
            convertedString
        } ?: ""
    }

}