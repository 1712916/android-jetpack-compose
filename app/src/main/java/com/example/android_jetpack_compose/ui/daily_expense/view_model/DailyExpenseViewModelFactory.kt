package com.example.android_jetpack_compose.ui.daily_expense.view_model

import androidx.lifecycle.*
import com.example.android_jetpack_compose.ui.setting_default_expense.view_model.*
import java.util.*

class DailyExpenseViewModelFactory(
    private val date: Date?,
    private val id: String? = null,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InputDailyExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InputDailyExpenseViewModel(date!!) as T
        } else if (modelClass.isAssignableFrom(UpdateDailyExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UpdateDailyExpenseViewModel(date!!, id) as T
        } else if (modelClass.isAssignableFrom(SettingDefaultInputExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingDefaultInputExpenseViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
