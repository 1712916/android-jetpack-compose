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
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.*
import org.json.*
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
    lateinit var expenseList: ListExpenseLiveData
    lateinit var totalMoney: LiveData<Long>
    val fireStore = Firebase.firestore
    val now = Date()

    init {
        expenseList = ListExpenseLiveData(
            fireStore.collection("smile.vinhnt@gmail.com")
                .document(SimpleDateFormat("MM-yyyy").format(now))
                .collection(SimpleDateFormat("dd-MM-yyyy").format(now)),
        )
        totalMoney = expenseList.map { it ->
            it.fold(0) { sum, e -> sum + e.money }
        }
        //        viewModelScope.launch {
        //            fireStore.collection("smile.vinhnt@gmail.com")
        //                .document(SimpleDateFormat("MM-yyyy").format(now))
        //                .collection(SimpleDateFormat("dd-MM-yyyy").format(now))
        //                .addSnapshotListener { s, e ->
        //                    Log.d(
        //                        "Log firebase",
        //                        s?.documents.toString()
        //                    )
        //
        //                    s?.documents?.forEach {
        //                        it.data?.let { it1 ->
        //                            Log.d(
        //                                "firebase document",
        //                                it1.getValue("createDate").toString(),
        //                                //                                        Json.decodeFromString<MoneyModel>(JSONObject(it1).toString())
        //                                //                                    .toString()
        //                            )
        //                        }
        //                    }
        //                }
        //        }

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
                Log.i("document", it.data.toString())

                moneyModels.add(
                    MoneyModel(
                        id = data.getValue("id") as String,
                        note = data.getValue("note") as String?,
                        expenseCategory = ExpenseCategory(
                            id = ((data.getValue("expenseCategory") as Map<*, *>)["id"] as Long).toInt(),
                            name = (data.getValue("expenseCategory") as Map<*, *>)["name"] as String
                        ),
                        money = data.getValue("money") as Long,
                        updateDate = Date(),
                        createDate = Date(),
                        expenseMethod = ExpenseMethod(
                            id = 1,
                            name = "",
                        ),
                        //                        expenseMethod = ExpenseMethod(
                        //                            id = (data.getValue("expenseMethod") as Map<*, *>)["id"] as Int,
                        //                            name = (data.getValue("expenseMethod") as Map<*, *>)["name"] as String
                        //                        ),
                    )
                )
            }

            value = moneyModels
        } else if (error != null) {
            TODO("Should handle errors")
        }
    }

}
