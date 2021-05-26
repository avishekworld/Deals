package com.target.targetcasestudy.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

sealed class NavEvent {
    data class ActivityNavEvent(val clazz: Class<*>, val bundle : Bundle) : NavEvent() {
        fun build(context : Context) : Intent {
            return Intent(context, clazz).apply {
                putExtras(bundle)
            }
        }
    }

    data class FragmentNavEvent(val clazz: Class<*>, val bundle : Bundle) : NavEvent() {
        fun build() : Fragment {
            return (clazz.getConstructor().newInstance() as Fragment).apply {
                this.arguments = bundle
            }
        }
    }
    data class FragmentDialogNavEvent(val clazz: Class<*>, val bundle : Bundle) : NavEvent() {
        fun build() : DialogFragment {
            return (clazz.getConstructor().newInstance() as DialogFragment).apply {
                this.arguments = bundle
            }
        }
    }
}
