package com.example.android_jetpack_compose.firebase_util

import com.example.android_jetpack_compose.entity.ExpenseMethod
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json

open interface FirebaseUtil {
    val fireStore: FirebaseFirestore
        get() = Firebase.firestore
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
