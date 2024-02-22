package com.example.android_jetpack_compose.ui.daily_expense.view_model

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.expense.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.datetime.*
import java.util.*

open class InputDailyExpenseViewModel(date: LocalDate) :
    DailyExpenseViewModel(InputDailyExpenseRepositoryImpl(date)) {
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

class UpdateDailyExpenseViewModel(date: LocalDate, id: String?) :
    DailyExpenseViewModel(InputDailyExpenseRepositoryImpl(date)) {
    init {
        viewModelScope.launch {
            if (id != null) {
                repository.read(id).onSuccess {
                    currentExpense = it

                    _uiState.update { currentState ->
                        currentState.copy(
                            money = currentExpense!!.money.toString(),
                            note = currentExpense!!.note,
                            method = currentExpense!!.method,
                            category = currentExpense!!.category,
                        )
                    }
                }
            }
        }
    }

    private var currentExpense: MoneyModel? = null
    override fun onSave(saveAnBack: Boolean) {
        //validate
        ///check required fields
        ///
        _uiState.value.let {
            if (!InputDailyExpenseStateValidator(it).validate()) {
                return
            }
            val model = currentExpense!!.copy(
                money = it.money!!.toLong(),
                method = it.method!!,
                category = it.category!!,
                note = it.note,
                updateDate = Date()
            )
            viewModelScope.launch {
                repository.update(currentExpense!!.id, model).onSuccess {
                    emitToast(SuccessAndBackToastMessage("Update expense successfully"))
                }.onFailure {
                    emitToast(FailureToastMessage("Update expense failed"))
                }
            }
        }
    }
}
