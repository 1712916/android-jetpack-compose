package com.example.android_jetpack_compose.ui.daily_expense.view

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_jetpack_compose.InputDailyExpense
import com.example.android_jetpack_compose.Notification
import com.example.android_jetpack_compose.R
import com.example.android_jetpack_compose.appNavController
import com.example.android_jetpack_compose.entity.ExpenseCategory
import com.example.android_jetpack_compose.entity.ExpenseMethod
import com.example.android_jetpack_compose.entity.MoneyModel
import com.example.android_jetpack_compose.firebase_util.GetExpenseMethod
import com.example.android_jetpack_compose.ui.daily_expense.view_model.DailyExpenseViewModel
import com.example.android_jetpack_compose.ui.dashboard.AppBar
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.*

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyExpenseView() {
    val viewModel: DailyExpenseViewModel = viewModel()

    val expenseListState = viewModel.expenseList.observeAsState()

    val totalExpenseState = viewModel.totalMoney.observeAsState()

    val database = Firebase.firestore

    database.collection("smile.vinhnt@gmail.com/11-2023/03-11-2023").addSnapshotListener({ n, a ->
        Log.d("addSnapshotListener", "${n?.documents}")
    })

    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            AppBar(
                title = "Today",
                showBackButton = true,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                appNavController?.navigate(InputDailyExpense.route)
//                try {
//
////                    database.collection("smile.vinhnt@gmail.com").document("11-2023")
////                        .collection("03-11-2023")
////                        .get().addOnSuccessListener { result ->
////                            Log.d("firebase-read", "${result.documents}")
////
////                        }
////                        .addOnFailureListener { exception ->
////                            Log.w("firebase-read", "Error getting documents.", exception)
////                        }
//
////                    database.collection("smile.vinhnt@gmail.com").document("11-2023")
////                        .collection("03-11-2023").add(mapOf("money" to 100000))
//                    coroutineScope.launch {
//                        Log.d(
//                            " GetExpenseMethod().getList()",
//                            GetExpenseMethod().getList().toString()
//                        )
//                    }
//                } catch (e: Exception) {
//                    Log.i("firestore", e.toString())
//                }

            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add expense"
                )
            }
        }
    ) { p ->
        Column(modifier = Modifier.padding(p)) {
            TotalExpenseView(totalExpenseState.value ?: 0)
            LazyColumn(
                modifier = Modifier.weight(1f),
                content = {
                    item {
                        ExpenseCard(
                            MoneyModel(
                                id = "1",
                                money = 35000,
                                note = null,
                                expenseCategory = ExpenseCategory(id = 1, name = "ăn sáng"),
                                expenseMethod = ExpenseMethod(name = "Tien mat", id = 0),
                                createDate = Date(),
                                updateDate = Date(),
                            )
                        )
                    }

                    items(expenseListState.value?.count() ?: 0) {
                        ExpenseCard(
                            expenseListState.value!![it]
                        )
                    }

                },
            )
        }
    }
}

@Composable
fun TotalExpenseView(totalExpense: Long) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(16.dp)
            .drawBehind {

                val strokeWidth = 10f
                val y = size.height - strokeWidth / 2

                drawLine(
                    Color.LightGray,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            },
        contentAlignment = Alignment.Center,

        ) {
        Text(
            text = "$totalExpense",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
        )

    }


}

@Composable
fun ExpenseCard(expense: MoneyModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RectangleShape

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = expense.expenseCategory.name.uppercase(Locale.ROOT),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${expense.money}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Text(text = expense.expenseMethod.name)

            if (!expense.note.isNullOrEmpty())
                Text(
                    text = "${expense.money}",
                    style = MaterialTheme.typography.labelLarge
                )
        }
    }
}
