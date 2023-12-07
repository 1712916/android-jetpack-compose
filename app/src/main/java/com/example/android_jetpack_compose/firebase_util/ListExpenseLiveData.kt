package com.example.android_jetpack_compose.firebase_util

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.mapping_firebase_object.*
import com.example.android_jetpack_compose.entity.*
import com.google.firebase.firestore.*
import kotlinx.coroutines.*

class ListExpenseLiveData(private val collectionReference: CollectionReference) :
    LiveData<List<MoneyModel>>(), EventListener<QuerySnapshot> {
    private var listenerRegistration: ListenerRegistration? = null
    private val mapping: MappingFirebaseObject<MoneyModel, MoneySaveObject> =
        MappingSavingMoneyModel()

    override fun onActive() {
        listenerRegistration = collectionReference.addSnapshotListener(this)
    }

    override fun onInactive() {
        listenerRegistration?.remove()
    }

    override fun onEvent(snapshot: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (snapshot != null) {
            val scope =
                CoroutineScope(Dispatchers.Default) // Use the dispatcher that fits your use case

            scope.launch {
                postValue(snapshot.map { mapping.getting(it) })
            }
        } else if (error != null) {
            TODO("Should handle errors")
        }
    }

}
