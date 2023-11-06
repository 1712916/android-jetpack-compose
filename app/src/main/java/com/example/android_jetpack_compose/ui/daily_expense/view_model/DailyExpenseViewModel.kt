package com.example.android_jetpack_compose.ui.daily_expense.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope

import com.example.android_jetpack_compose.data.expense.DailyExpenseRepository
import com.example.android_jetpack_compose.data.expense.DailyExpenseRepositoryImpl
import com.example.android_jetpack_compose.entity.ExpenseCategory
import com.example.android_jetpack_compose.entity.ExpenseMethod
import com.example.android_jetpack_compose.entity.MoneyModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat

import java.util.Date
import java.util.Timer
import kotlin.concurrent.schedule

/*
* Total:
* List: MoneyModel
* */
class DailyExpenseViewModel :
    ViewModel() {
    private val dailyExpenseRepository: DailyExpenseRepository = DailyExpenseRepositoryImpl()

    val expenseList: LiveData<List<MoneyModel>> =
        dailyExpenseRepository.getList()

    val totalMoney: LiveData<Long> = expenseList.map { it ->
        it.fold(0) { sum, e -> sum + e.money }
    }

    val fireStore = Firebase.firestore

    val now = Date()

    init {
//        Timer("SettingUp", false).schedule(1500, action = {
//            dailyExpenseRepository.create(
//                MoneyModel(
//                    id = 1,
//                    money = 35000,
//                    note = null,
//                    expenseCategory = ExpenseCategory.Expense(id = 1, name = "ăn sáng"),
//                    expenseMethod = ExpenseMethod(name = "Tien mat", id = 0),
//                    createDate = Date(),
//                    updateDate = Date(),
//                )
//            )
//        })
        viewModelScope.launch {
            fireStore.collection("smile.vinhnt@gmail.com")
                .document(SimpleDateFormat("MM-yyyy").format(now))
                .collection(SimpleDateFormat("dd-MM-yyyy").format(now))
                .get().addOnSuccessListener {
                    Log.d(
                        "Log firebase",
//                        it.documents.map { d -> Json.decodeFromString<MoneyModel>(d.data.toString()) }
//                            .toString()
                        ""
                    )
                }

            fireStore.collection("smile.vinhnt@gmail.com")
                .document(SimpleDateFormat("MM-yyyy").format(now))
                .collection(SimpleDateFormat("dd-MM-yyyy").format(now)).document()
//                .set(MoneyModel.se)
        }


    }

    fun add(expense: MoneyModel) {
        dailyExpenseRepository.create(
            expense
        )
    }

    fun remove(expense: MoneyModel) {
    }

    fun update(index: Int, expense: MoneyModel) {
    }

    fun save() {
    }
}
