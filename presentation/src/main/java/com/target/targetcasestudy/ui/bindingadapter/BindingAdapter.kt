package com.target.targetcasestudy.ui.bindingadapter

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("strikeThrough")
fun setStrikeThroughText(textView: TextView, strike: Boolean) {
    when(strike) {
        true -> textView.paintFlags = (textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
        false -> textView.paintFlags = (textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv())
    }
}