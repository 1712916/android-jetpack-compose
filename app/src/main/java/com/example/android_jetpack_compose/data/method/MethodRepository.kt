package com.example.android_jetpack_compose.data.method

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.*
import com.example.android_jetpack_compose.data.category.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.firebase_util.*
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.*

var methods: List<ExpenseMethod> = emptyList()

abstract class MethodRepository : CRUDRepository<ExpenseMethod, Int>,
    LiveDataList<ExpenseMethod>

class MethodRepositoryImpl : MethodRepository(), FirebaseUtil {
    private var collection: CollectionReference =
        fireStore.collection("method")

    override suspend fun create(item: ExpenseMethod): Result<ExpenseMethod> {
        try {
            collection.document().set(item).await()

            return Result.success(item)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun read(id: Int): Result<ExpenseMethod?> {
        val rs = collection.whereEqualTo("id", id).limit(1).get().await()

        if (rs != null && !rs.isEmpty) {
            return Result.success(
                ExpenseMethod(
                    id = rs.first().data["id"] as Int,
                    name = rs.first().data["name"] as String,
                ),
            )
        }

        return Result.failure(exception = Exception("Not found item"))

    }

    override suspend fun update(id: Int, newItem: ExpenseMethod): Result<ExpenseMethod> {
        val rs = collection.whereEqualTo("id", id).limit(1).get().await()


        if (rs != null && !rs.isEmpty) {
            rs.first().reference.set(newItem.copy(id = id)).await()

            return Result.success(
                newItem.copy(id = id)
            )
        }

        return Result.failure(exception = Exception("Not found item"))
    }

    override suspend fun delete(id: Int): Result<ExpenseMethod?> {
        val rs = collection.whereEqualTo("id", id).limit(1).get().await()
        if (rs != null && !rs.isEmpty) {
            val item = ExpenseMethod(
                id = rs.first().data["id"] as Int,
                name = rs.first().data["name"] as String,
            )
            rs.first().reference.delete()

            return Result.success(
                item
            )
        }

        return Result.failure(exception = Exception("Not found item"))
    }

    override fun getLiveDataList(): LiveData<List<ExpenseMethod>> {
        return ListMethodLiveData(collection)
    }
}

class ListMethodLiveData(private val collectionReference: CollectionReference) :
    LiveData<List<ExpenseMethod>>(), EventListener<QuerySnapshot> {
    private var listenerRegistration: ListenerRegistration? = null
    override fun onActive() {
        listenerRegistration = collectionReference.addSnapshotListener(this)
    }

    override fun onInactive() {
        listenerRegistration?.remove()
    }

    override fun onEvent(snapshot: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (snapshot != null && !snapshot.isEmpty) {
            val categories: MutableList<ExpenseMethod> = mutableListOf()

            snapshot.forEach {
                val data = it.data

                categories.add(
                    ExpenseMethod(
                        id = (data.getValue("id") as Long).toInt(),
                        name = data.getValue("name") as String,
                    )
                )
            }

            value = categories
        } else if (error != null) {
            TODO("Should handle errors")
        }
    }

}
