package com.example.android_jetpack_compose.data.category

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.firebase_util.*
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import kotlinx.coroutines.tasks.*

abstract class CategoryRepository : CRUDRepository<ExpenseCategory, Int>,
    LiveDataList<ExpenseCategory>

class CategoryRepositoryImpl : CategoryRepository(), FirebaseUtil {
    private var collection: CollectionReference =
        fireStore.collection("category")

    override suspend fun create(item: ExpenseCategory): Result<ExpenseCategory> {
        try {
            collection.document().set(item).await()

            return Result.success(item)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun read(id: Int): Result<ExpenseCategory?> {
        val rs = collection.whereEqualTo("id", id).limit(1).get().await()

        if (rs != null && !rs.isEmpty) {
            return Result.success(
                ExpenseCategory(
                    id = rs.first().data["id"] as Int,
                    name = rs.first().data["name"] as String,
                ),
            )
        }

        return Result.failure(exception = Exception("Not found item"))

    }

    override suspend fun update(id: Int, newItem: ExpenseCategory): Result<ExpenseCategory> {
        val rs = collection.whereEqualTo("id", id).limit(1).get().await()


        if (rs != null && !rs.isEmpty) {
            rs.first().reference.set(newItem.copy(id = id)).await()

            return Result.success(
                newItem.copy(id = id)
            )
        }

        return Result.failure(exception = Exception("Not found item"))
    }

    override suspend fun delete(id: Int): Result<ExpenseCategory?> {
        val rs = collection.whereEqualTo("id", id).limit(1).get().await()
        if (rs != null && !rs.isEmpty) {
            val item = ExpenseCategory(
                id = (rs.first().data["id"] as Long).toInt(),
                name = rs.first().data["name"] as String,
            )
            rs.first().reference.delete()

            return Result.success(
                item
            )
        }

        return Result.failure(exception = Exception("Not found item"))
    }

    override fun getLiveDataList(): LiveData<List<ExpenseCategory>> {
        return ListCategoryLiveData(collection)
    }

}

class ListCategoryLiveData(private val collectionReference: CollectionReference) :
    LiveData<List<ExpenseCategory>>(), EventListener<QuerySnapshot> {
    private var listenerRegistration: ListenerRegistration? = null
    override fun onActive() {
        listenerRegistration = collectionReference.addSnapshotListener(this)
    }

    override fun onInactive() {
        listenerRegistration?.remove()
    }

    override fun onEvent(snapshot: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (snapshot != null && !snapshot.isEmpty) {
            val categories: MutableList<ExpenseCategory> = mutableListOf()

            snapshot.forEach {
                val data = it.data

                categories.add(
                    ExpenseCategory(
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
