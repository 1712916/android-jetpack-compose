package com.example.android_jetpack_compose.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppNumberBoard(onChanged: (String) -> Unit, text: String) {
    val removeKey = "remove"
    val clearKey = "clear"
    val inputBoard = arrayOf(
        1, 2, 3,
        4, 5, 6,
        7, 8, 9,
        clearKey, 0, removeKey
    )


    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        userScrollEnabled = false

    ) {
        items(inputBoard.size) { index ->
            TextButton(onClick = {
                when (val key = inputBoard[index]) {
                    removeKey -> onChanged(text.dropLast(1))
                    clearKey -> onChanged("")
                    else -> onChanged(text + key)
                }
            },
            shape = CircleShape,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.White
                )

                ) {
                when (val key = inputBoard[index]) {
                    removeKey -> Icon(imageVector = Icons.Default.ArrowBack, contentDescription = key.toString())
                    clearKey -> Icon(imageVector = Icons.Default.Refresh, contentDescription = key.toString())
                    else ->           Text(text = "${inputBoard[index]}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun AppNumberBoardPreview() {
    AppNumberBoard(onChanged = { it -> print(it) }, text = "")
}
