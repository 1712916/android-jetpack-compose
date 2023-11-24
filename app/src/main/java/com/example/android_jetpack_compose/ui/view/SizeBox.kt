package com.example.android_jetpack_compose.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*

@Composable
fun HeightBox(height: Double) = Spacer(modifier = Modifier.height(height.dp))
@Composable
fun WidthBox(width: Double) = Spacer(modifier = Modifier.width(width.dp))
@Composable
fun SizedBox(width: Double = 0.0, height: Double = 0.0) = Spacer(
    modifier = Modifier
        .height(height.dp)
        .width(width.dp)
)
