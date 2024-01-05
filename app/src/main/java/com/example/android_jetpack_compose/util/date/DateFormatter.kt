package com.example.android_jetpack_compose.util.date

import java.text.*
import java.util.*

fun Date.format(pattern: String = "dd-MM-yyyy"): String {
    return SimpleDateFormat(pattern).format(this)
}
