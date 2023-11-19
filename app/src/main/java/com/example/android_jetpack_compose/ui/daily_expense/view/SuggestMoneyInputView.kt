package com.example.android_jetpack_compose.ui.daily_expense.view

import android.widget.HorizontalScrollView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.example.android_jetpack_compose.ui.dashboard.*
import com.example.android_jetpack_compose.util.*

@Preview
@Composable
fun  SuggestMoneyInputPreView() {
    SuggestMoneyInputView("1", output = {})
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SuggestMoneyInputView (input: String, output: (String) -> Unit){
    val suggestList = List(5) {
        input +"0".repeat(it +2)
    }

    if (input.isEmpty() || input == "0")
        HeightBox(height = 0.0)
    else
        LazyRow() {
            items(suggestList) {
                Box(modifier = Modifier.padding(end = 4.dp)) {
                    Chip(
                        onClick = { output(it) }) {
                        Text(text = FormatMoneyInput(it).toString())
                    }
                }
            }
        }
}
