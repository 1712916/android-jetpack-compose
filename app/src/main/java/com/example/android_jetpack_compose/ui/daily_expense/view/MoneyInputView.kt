package com.example.android_jetpack_compose.ui.daily_expense.view

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
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency

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
        modifier = Modifier.fillMaxWidth().height(80.dp).background(Color.White)
            .border(1.dp, validateState.color()).padding(all = 16.dp),
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
                FormatMoneyInput(number).toString(), modifier = Modifier.weight(1f),
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

class FormatMoneyInput(private val number: String) {
    private val format: NumberFormat = DecimalFormat("#,###").apply {
        maximumFractionDigits = 0
        currency = Currency.getInstance("EUR")
    }

    override fun toString(): String {
        return try {
            format.format(number.toLong()).replace(",", ".")
        } catch (e: Exception) {
            number
        }
    }
}

@Composable
fun SuggestMoneyList(number: String) {

}
