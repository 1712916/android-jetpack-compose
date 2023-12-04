package com.example.android_jetpack_compose.firebase_util

import com.example.android_jetpack_compose.entity.*
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.*
import kotlinx.coroutines.tasks.*
import kotlinx.serialization.json.*

open interface FirebaseUtil {
    val fireStore: FirebaseFirestore
        get() = Firebase.firestore
}

open interface AppAuthFirebaseUtil : FirebaseUtil {
    val authCollection: CollectionReference
        get() = fireStore.collection(AppUser.getInstance().getEmail())

    val collection: CollectionReference
}

interface GetData<T> {
    suspend fun getList(): List<T>
}

class GetExpenseMethod : FirebaseUtil, GetData<ExpenseMethod> {
    override suspend fun getList(): List<ExpenseMethod> {
        return fireStore.collection("method").get()
            .await().documents.map { d -> Json.decodeFromString<ExpenseMethod>(d.toString()) }
    }
}
