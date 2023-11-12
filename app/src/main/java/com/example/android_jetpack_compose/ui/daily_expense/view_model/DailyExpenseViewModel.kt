package com.example.android_jetpack_compose.ui.daily_expense.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.android_jetpack_compose.data.expense.DailyExpenseRepository
import com.example.android_jetpack_compose.data.expense.DailyExpenseRepositoryImpl
import com.example.android_jetpack_compose.entity.ExpenseCategory
import com.example.android_jetpack_compose.entity.ExpenseMethod
import com.example.android_jetpack_compose.entity.MoneyModel
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date

/*
* Total:
* List: MoneyModel
* */
class DailyExpenseViewModel(val date: Date) :
    ViewModel() {
    private val dailyExpenseRepository: DailyExpenseRepository = DailyExpenseRepositoryImpl()
    lateinit var expenseList: ListExpenseLiveData
    lateinit var totalMoney: LiveData<Long>
    val fireStore = Firebase.firestore

    init {
        expenseList = ListExpenseLiveData(
            fireStore.collection("smile.vinhnt@gmail.com")
                .document(SimpleDateFormat("MM-yyyy").format(date))
                .collection(SimpleDateFormat("dd-MM-yyyy").format(date)),
        )
        totalMoney = expenseList.map { it ->
            it.fold(0) { sum, e -> sum + e.money }
        }
    }

    fun add(expense: MoneyModel) {
        dailyExpenseRepository.create(
            expense
        )
    }

    fun remove(expense: MoneyModel) {}
}

class ListExpenseLiveData(private val collectionReference: CollectionReference) :
    LiveData<List<MoneyModel>>(), EventListener<QuerySnapshot> {
    private var listenerRegistration: ListenerRegistration? = null
    override fun onActive() {
        listenerRegistration = collectionReference.addSnapshotListener(this)
    }

    override fun onInactive() {
        listenerRegistration?.remove()
    }

    override fun onEvent(snapshot: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (snapshot != null && !snapshot.isEmpty) {
            val moneyModels: MutableList<MoneyModel> = mutableListOf()

            snapshot.forEach {
                val data = it.data

                moneyModels.add(
                    MoneyModel(
                        id = data.getValue("id") as String,
                        note = data.getValue("note") as String?,
                        money = data.getValue("money") as Long,
                        updateDate = it.getTimestamp("updateDate")?.toDate(),
                        createDate = it.getTimestamp("createDate")?.toDate(),
                        expenseCategory = ExpenseCategory(
                            id = ((data.getValue("expenseCategory") as Map<*, *>)["id"] as Long).toInt(),
                            name = (data.getValue("expenseCategory") as Map<*, *>)["name"] as String
                        ),
                        expenseMethod = ExpenseMethod(
                            id = ((data.getValue("expenseMethod") as Map<*, *>)["id"] as Long).toInt(),
                            name = (data.getValue("expenseMethod") as Map<*, *>)["name"] as String
                        ),
                    )
                )
            }

            value = moneyModels
        } else if (error != null) {
            TODO("Should handle errors")
        }
    }

}
