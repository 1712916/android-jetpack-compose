package com.example.android_jetpack_compose.ui.daily_expense.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
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
fun ExpenseCard(
    expense: MoneyModel,
    onClick: (() -> Unit)? = null,
    showDelete: Boolean = false,
    onDelete: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick?.invoke()
            },
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = expense.expenseCategory.name.uppercase(Locale.ROOT),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${expense.money.money()}",
                    style = MaterialTheme.typography.bodySmall
                )
                if (showDelete)
                    IconButton(
                        onClick = onDelete ?: {},
                        modifier = Modifier
                            .width(16.dp)
                            .height(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete expense"
                        )
                    }
            }
            //            Text(text = expense.expenseMethod.name)
            if (!expense.note.isNullOrEmpty())
                Text(
                    text = "Note: ${expense.note}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 14.sp
                    )
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
