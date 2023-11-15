package com.example.android_jetpack_compose.util

import android.content.*
import android.widget.*

open class ShowToastMessage(private val message: String) {
    fun show(context: Context) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT,
        ).show()
    }
}

class SuccessToastMessage(message: String) : ShowToastMessage(message) {}
class FailureToastMessage(message: String) : ShowToastMessage(message) {}
