package com.example.android_jetpack_compose.ui.daily_expense.view_model

import android.content.*
import android.widget.*
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.expense.*
import com.example.android_jetpack_compose.entity.ExpenseCategory
import com.example.android_jetpack_compose.entity.ExpenseMethod
import com.example.android_jetpack_compose.entity.MoneyModel
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.security.*
import java.util.*

/*
* requires: money, category, method,
* optional: note
*
* Format number:
*  example: 001 -> 1
*  example: 1002 -> 1.002
*
* Suggest:
*  example: 15 -> 15.000, 150.000, 1.500.000
* */

class InputDailyExpenseViewModelFactory(
    private val date: Date
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InputDailyExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InputDailyExpenseViewModel(date) as T
        } else if (modelClass.isAssignableFrom(UpdateDailyExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UpdateDailyExpenseViewModel(date) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}

open class InputDailyExpenseViewModel(date: Date) : MapStateViewModel() {
    val repository: DailyExpenseRepository = DailyExpenseRepositoryImpl(date)
    val _toastState = MutableSharedFlow<ShowToastMessage?>()
    val toastState = _toastState.asSharedFlow()
    val _uiState = MutableStateFlow(InputDailyExpenseState())
    val uiState: StateFlow<InputDailyExpenseState> = _uiState.asStateFlow()
    val validateState: StateFlow<Boolean> = _uiState.mapState {
        InputDailyExpenseStateValidator(it).validate()
    }

    fun changeMoney(moneyString: String) {
        _uiState.update { currentState ->
            currentState.copy(
                money = moneyString
            )
        }
    }

    fun changeNote(note: String) {
        _uiState.update { currentState ->
            currentState.copy(
                note = note
            )
        }
    }

    open fun onSave(saveAnBack: Boolean = true) {
        _uiState.value.let {
            if (!InputDailyExpenseStateValidator(it).validate()) {
                return
            }
            val model = MoneyModel(
                id = "",
                money = it.money!!.toLong(),
                expenseMethod = it.method!!,
                expenseCategory = it.category!!,
                note = it.note,
                createDate = Date(),
                updateDate = Date()
            )

            viewModelScope.launch {
                repository.create(model).onSuccess {
                    if (saveAnBack) {
                        _toastState.emit(SuccessAndBackToastMessage("Add expense successfully"))

                    } else {
                        _toastState.emit(SuccessToastMessage("Add expense successfully"))
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
                    _toastState.emit(FailureToastMessage("Add expense failed"))
                }
            }
        }
    }

    fun changeMethod(method: ExpenseMethod) {
        _uiState.update { currentState ->
            currentState.copy(
                method = method
            )
        }
    }

    fun changeCategory(category: ExpenseCategory) {
        _uiState.update { currentState ->
            currentState.copy(
                category = category
            )
        }
    }
}

class UpdateDailyExpenseViewModel(date: Date) : InputDailyExpenseViewModel(date) {
    private var id: String? = null
    private var currentExpense: MoneyModel? = null
    suspend fun loadById(id: String?) {
        if (id == null) {
            return
        }

        this.id = id
        repository.read(id).onSuccess {
            currentExpense = it

            _uiState.update { currentState ->
                currentState.copy(
                    money = currentExpense!!.money.toString(),
                    note = currentExpense!!.note,
                    method = currentExpense!!.expenseMethod,
                    category = currentExpense!!.expenseCategory,
                )
            }
        }
    }

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
                expenseMethod = it.method!!,
                expenseCategory = it.category!!,
                note = it.note,
                updateDate = Date()
            )
            viewModelScope.launch {
                repository.update(currentExpense!!.id, model).onSuccess {
                    _toastState.emit(SuccessToastMessage("Update expense successfully"))
                }.onFailure {
                    _toastState.emit(FailureToastMessage("Update expense failed"))
                }
            }
        }
        ///create date
        ///update date
    }
}
