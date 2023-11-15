package com.example.android_jetpack_compose.firebase_util

import androidx.lifecycle.*
import com.example.android_jetpack_compose.entity.*
import com.google.firebase.firestore.*

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
