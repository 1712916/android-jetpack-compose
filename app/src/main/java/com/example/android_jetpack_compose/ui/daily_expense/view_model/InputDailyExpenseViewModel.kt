package com.example.android_jetpack_compose.ui.daily_expense.view_model

import android.content.*
import android.widget.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_jetpack_compose.entity.ExpenseCategory
import com.example.android_jetpack_compose.entity.ExpenseMethod
import com.example.android_jetpack_compose.entity.MoneyModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
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
fun <T, K> StateFlow<T>.mapState(
    scope: CoroutineScope,
    transform: (data: T) -> K
): StateFlow<K> {
    return mapLatest {
        transform(it)
    }
        .stateIn(scope, SharingStarted.Eagerly, transform(value))
}

fun <T, K> StateFlow<T>.mapState(
    scope: CoroutineScope,
    initialValue: K,
    transform: suspend (data: T) -> K
): StateFlow<K> {
    return mapLatest {
        transform(it)
    }
        .stateIn(scope, SharingStarted.Eagerly, initialValue)
}

abstract class BaseViewModel : ViewModel() {
    fun <T, K> StateFlow<T>.mapState(
        transform: (data: T) -> K
    ): StateFlow<K> {
        return mapState(
            scope = viewModelScope,
            transform = transform
        )
    }

    fun <T, K> StateFlow<T>.mapState(
        initialValue: K,
        transform: suspend (data: T) -> K
    ): StateFlow<K> {
        return mapState(
            scope = viewModelScope,
            initialValue = initialValue,
            transform = transform
        )
    }
}

class InputDailyExpenseViewModel : BaseViewModel() {
    private val _toastState = MutableStateFlow<ShowToastMessage?>(null)
    val toastState = _toastState.asStateFlow()
    // Game UI state
    private val _uiState = MutableStateFlow(
        InputDailyExpenseState(
        )
    )
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

    fun onSave() {
        //validate
        ///check required fields
        ///
        _uiState.value.let {
            if (!InputDailyExpenseStateValidator(it).validate()) {
                return
            }
            val fireStore = Firebase.firestore
            val now = Date()
            val ref = fireStore.collection("smile.vinhnt@gmail.com")
                .document(SimpleDateFormat("MM-yyyy").format(now))
                .collection(SimpleDateFormat("dd-MM-yyyy").format(now))
                .document()
            val model = MoneyModel(
                id = ref.id,
                money = it.money!!.toLong(),
                expenseMethod = it.method!!,
                expenseCategory = it.category!!,
                note = it.note,
                createDate = Date(),
                updateDate = Date()
            )


            ref.set(model)
                .addOnSuccessListener {
                    _toastState.value = SuccessToastMessage("Add expense successfully")
                }.addOnFailureListener {
                    _toastState.value = FailureToastMessage("Add expense failed")
                }
        }
        ///create date
        ///update date
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

data class InputDailyExpenseState(
    val money: String? = null, val note: String? = null,
    val method: ExpenseMethod? = null, val category: ExpenseCategory? = null,
)

class InputDailyExpenseStateValidator(private val inputDailyExpenseState: InputDailyExpenseState) {
    fun validate(): Boolean {
        return !inputDailyExpenseState.money.isNullOrEmpty() && inputDailyExpenseState.method != null && inputDailyExpenseState.category != null
    }
}

open class ShowToastMessage(private val message: String) {
    fun show(context: Context) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT,
        ).show()
    }
}

class SuccessToastMessage(message: String) : ShowToastMessage(message) {}
class FailureToastMessage(message: String) : ShowToastMessage(message) {}
