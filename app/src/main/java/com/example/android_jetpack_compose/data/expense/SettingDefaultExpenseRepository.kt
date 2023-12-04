package com.example.android_jetpack_compose.data.expense

import androidx.lifecycle.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.firebase_util.*
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.*

class SettingDefaultExpenseRepositoryImpl : DailyExpenseRepository(), AppAuthFirebaseUtil {
    override val collection: CollectionReference
        get() = authCollection.document("setting").collection("default_expense")

    override suspend fun create(item: MoneyModel): Result<MoneyModel> {
        val ref = collection.document()
        val storageItem = item.copy(id = ref.id)
        ref.set(storageItem).addOnSuccessListener { }.addOnFailureListener {
            throw Exception("Could not create item")
        }

        return Result.success(storageItem)
    }

    override suspend fun read(id: String): Result<MoneyModel?> {
        val it = collection.document(id).get().await()

        if (it == null || !it.exists()) {
            throw Exception("Unable to get expense by id")
        }

        return Result.success(it.toObject())
    }

    override suspend fun update(id: String, newItem: MoneyModel): Result<MoneyModel> {
        val ref = collection.document(id)
        val storageItem = newItem.copy(id = ref.id)

        ref.set(storageItem).addOnFailureListener {
            throw Exception("Could not update item")
        }

        return Result.success(storageItem)
    }

    override suspend fun delete(id: String): Result<MoneyModel?> {
        val rs = collection.whereEqualTo("id", id).limit(1).get().await()

        if (rs != null && !rs.isEmpty) {
            val item: MoneyModel = rs.first().toObject()

            rs.first().reference.delete().await()

            return Result.success(
                item
            )
        }

        return Result.failure(Exception("Not found expense"))
    }

    override fun getLiveDataList(): LiveData<List<MoneyModel>> {
        return ListExpenseLiveData(
            collection
        )
    }

    override suspend fun getList(): Result<List<MoneyModel>> {
        val response = collection.get().await()

        if (response != null && !response.isEmpty) {
            val list = mutableListOf<MoneyModel>()

            response.documents.forEach { document ->
                list.add(
                    document.toObject()!!
                )
            }

            return Result.success(list)
        }

        return Result.success(emptyList())
    }

}
