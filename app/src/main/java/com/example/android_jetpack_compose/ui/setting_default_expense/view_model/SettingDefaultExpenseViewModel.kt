package com.example.android_jetpack_compose.ui.setting_default_expense.view_model

import android.util.*
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.category.*
import com.example.android_jetpack_compose.data.expense.*
import com.example.android_jetpack_compose.data.method.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.ui.daily_expense.view_model.*
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.*
import kotlinx.coroutines.*
import java.util.Date

/*
* Total:
* List: MoneyModel
* */
class SettingDefaultExpenseViewModel() :
    DailyExpenseListViewModel(SettingDefaultExpenseRepositoryImpl()) {
}
