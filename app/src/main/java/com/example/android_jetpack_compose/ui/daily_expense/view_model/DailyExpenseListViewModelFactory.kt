package com.example.android_jetpack_compose.ui.daily_expense.view_model

import androidx.lifecycle.*
import com.example.android_jetpack_compose.ui.setting_default_expense.view_model.*
import kotlinx.datetime.*

class DailyExpenseListViewModelFactory(val date: LocalDate) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyExpenseListViewModelImpl::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DailyExpenseListViewModelImpl(date) as T
        } else if (modelClass.isAssignableFrom(SettingDefaultExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingDefaultExpenseViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
