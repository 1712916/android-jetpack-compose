package com.example.android_jetpack_compose.util

import androidx.compose.ui.graphics.*
import java.security.*

fun generateColor(name: String): Color {
    // Use SHA-256 hash to convert the name to a hexadecimal code
    val bytes = MessageDigest.getInstance("SHA-256").digest(name.toByteArray())
    val hexCode = bytes.joinToString("") { "%01x".format(it) }

    // Extract parts of the hash code to determine RGB values
    val r = hexCode.substring(0, 2).toInt(16)
    val g = hexCode.substring(2, 4).toInt(16)
    val b = hexCode.substring(4, 6).toInt(16)

    return Color(r, g, b)
}

fun main() {
    // Example data
    val listName = "example_list_name"

    // Generate color based on the list name
    val color = generateColor(listName)

    // Display the result
    println("Color for '$listName': RGB$color")
}
