package com.example.android_jetpack_compose.data.method

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.firebase_util.*
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.*

abstract class MethodRepository : CRUDRepository<ExpenseMethod, String>,
    LiveDataList<ExpenseMethod>

class MethodRepositoryImpl : MethodRepository(), AppAuthFirebaseUtil {
    override var collection: CollectionReference =
        authCollection.document("method")
            .collection("method_list")

    override suspend fun create(item: ExpenseMethod): Result<ExpenseMethod> {
        try {
            collection.document().set(item).await()

            return Result.success(item)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun read(id: String): Result<ExpenseMethod?> {
        val rs = collection.whereEqualTo("id", id).limit(1).get().await()

        if (rs != null && !rs.isEmpty) {
            return Result.success(
                rs.first().toObject()
            )
        }

        return Result.failure(exception = Exception("Not found item"))

    }

    override suspend fun update(id: String, newItem: ExpenseMethod): Result<ExpenseMethod> {
        val rs = collection.whereEqualTo("id", id).limit(1).get().await()


        if (rs != null && !rs.isEmpty) {
            rs.first().reference.set(newItem.copy(id = id)).await()

            return Result.success(
                newItem.copy(id = id)
            )
        }

        return Result.failure(exception = Exception("Not found item"))
    }

    override suspend fun delete(id: String): Result<ExpenseMethod?> {
        val rs = collection.whereEqualTo("id", id).limit(1).get().await()
        if (rs != null && !rs.isEmpty) {
            val item: ExpenseMethod = rs.first().toObject()
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
                categories.add(
                    it.toObject()
                )
            }

            value = categories
        } else if (error != null) {
            TODO("Should handle errors")
        }
    }

}
