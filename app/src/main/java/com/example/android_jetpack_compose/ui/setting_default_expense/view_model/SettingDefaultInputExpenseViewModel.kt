package com.example.android_jetpack_compose.ui.setting_default_expense.view_model

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.expense.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.ui.daily_expense.view_model.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

class SettingDefaultInputExpenseViewModel :
    DailyExpenseViewModel(SettingDefaultExpenseRepositoryImpl()) {
    override fun onSave(saveAnBack: Boolean) {
        _uiState.value.let {
            if (!InputDailyExpenseStateValidator(it).validate()) {
                return
            }
            val model = MoneyModel(
                id = "",
                money = it.money!!.toLong(),
                method = it.method!!,
                category = it.category!!,
                note = it.note,
                createDate = Date(),
                updateDate = Date()
            )

            viewModelScope.launch {
                repository.create(model).onSuccess {
                    if (saveAnBack) {
                        emitToast(SuccessAndBackToastMessage("Add expense successfully"))
                    } else {
                        emitToast(SuccessToastMessage("Add expense successfully"))
                        _uiState.update { currentState ->
                            currentState.copy(
                                money = "",
                                method = null,
                                category = null,
                                note = null,
                            )
                        }
                    }
                }.onFailure {
                    emitToast(FailureToastMessage("Add expense failed"))
                }
            }
        }
    }
}
