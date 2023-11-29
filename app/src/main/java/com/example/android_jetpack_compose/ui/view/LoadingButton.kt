package com.example.android_jetpack_compose.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*

@Preview
@Composable
fun LoadingButtonPreview() {
    LoadingButton(
        isLoading = false, onClick = {},
        content = {
            Text("Hello Button")
        },
    )
}

@Composable
fun LoadingButton(
    isLoading: Boolean = false,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            if (!isLoading) {
                onClick()
            }
        },
    ) {
        if (isLoading)
            CircularProgressIndicator(
                color = Color.White
            )
        else content()
    }
}
