package com.example.android_jetpack_compose.ui.view

import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.platform.*

@Composable
fun BaseScreen(
     body: @Composable () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        },

    ) {
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(it)) {
            body()
        }
    }
}
