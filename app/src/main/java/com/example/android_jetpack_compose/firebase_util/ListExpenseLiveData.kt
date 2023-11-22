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
        if (snapshot != null) {
            value = snapshot.map { it.toObject() }
        } else if (error != null) {
            TODO("Should handle errors")
        }
    }

}
