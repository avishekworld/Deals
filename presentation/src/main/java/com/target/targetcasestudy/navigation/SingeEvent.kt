package com.target.targetcasestudy.navigation

class SingeEvent<T>(private val content : T) {
    private var consumed = false

    fun consume() : T? {
        return if (consumed) null
        else {
            consumed = true
            content
        }
    }
}