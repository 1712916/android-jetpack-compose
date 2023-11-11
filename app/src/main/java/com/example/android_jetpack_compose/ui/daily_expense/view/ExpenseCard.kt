package com.example.android_jetpack_compose.ui.daily_expense.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import java.util.*

@Composable
fun ExpenseCard(expense: MoneyModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = expense.expenseCategory.name.uppercase(Locale.ROOT),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${expense.money.money()}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            //            Text(text = expense.expenseMethod.name)
            if (!expense.note.isNullOrEmpty())
                Text(
                    text = "${expense.money}",
                    style = MaterialTheme.typography.labelLarge
                )
        }
    }
}
@Preview
@Composable
fun ExpenseCardPreview() {
    ExpenseCard(
        expense = MoneyModel(
            id = "",
            expenseMethod = ExpenseMethod(id = 1, name = ""),
            expenseCategory = ExpenseCategory(id = 1, name = "Ăn sáng"),
            money = 35000,
            note = "",
            createDate = Date(),
            updateDate = Date(),
        )
    )
}
