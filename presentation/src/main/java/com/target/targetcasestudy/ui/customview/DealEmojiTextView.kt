package com.target.targetcasestudy.ui.customview

import android.content.Context
import android.util.AttributeSet
import androidx.emoji.widget.EmojiTextView
import life.avishekworld.data.util.EmojiUtil
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DealEmojiTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : EmojiTextView(context, attrs, defStyle), KoinComponent {

    private val emojiUtil : EmojiUtil by inject()

    override fun setText(text: CharSequence?, type: BufferType?) {
        when {
            text != null && text != "" -> {
                val convertedText = emojiUtil.stringToEmojiUnicode(text.toString())
                super.setText(convertedText, type)
            }
            else -> {
                super.setText(text, type)
            }
        }
    }
}