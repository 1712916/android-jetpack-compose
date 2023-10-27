package com.example.android_jetpack_compose.ui.daily_expense

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_jetpack_compose.ui.dashboard.WidthBox

sealed class InputValidateState {
    abstract fun color(): Color
}

object ValidInputState : InputValidateState() {
    override fun color(): Color {
        return Color.Green
    }
}

object EmptyInputState : InputValidateState() {
    override fun color(): Color {
        return Color.Red
    }
}
object SameInputState : InputValidateState() {
    override fun color(): Color {
        return Color.Gray
    }
}


@Composable
fun MoneyInputView(number: String, validateState: InputValidateState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.White)
            .border(1.dp, validateState.color())
            .padding(all = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row {
            Text(
                text = "VND",
                style = TextStyle(
                    color = Color.Gray
                ),
            )
            WidthBox(width = 16.0)
            Text(
                number, modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                style = TextStyle(),
            )
        }
    }
}

@Preview
@Composable
fun MoneyInputViewPreview() {
    MoneyInputView(number = "35000", validateState = ValidInputState)
}
