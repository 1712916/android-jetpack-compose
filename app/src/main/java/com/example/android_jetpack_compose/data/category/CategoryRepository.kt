package com.example.android_jetpack_compose.data.category

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.firebase_util.*
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.*

abstract class CategoryRepository : CRUDRepository<ExpenseCategory, String>,
    LiveDataList<ExpenseCategory>

class CategoryRepositoryImpl : CategoryRepository(), AppAuthFirebaseUtil {
    override val collection: CollectionReference =
        authCollection.document("category")
            .collection("category_list")

    override suspend fun create(item: ExpenseCategory): Result<ExpenseCategory> {
        try {
            val doc = collection.document()

            doc.set(item.copy(id = doc.id)).await()

            return Result.success(item)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun read(id: String): Result<ExpenseCategory?> {
        val rs = collection.whereEqualTo("value", id).limit(1).get().await()

        if (rs != null && !rs.isEmpty) {
            return Result.success(rs.first().toObject())
        }

        return Result.failure(exception = Exception("Not found item"))

    }

    override suspend fun update(id: String, newItem: ExpenseCategory): Result<ExpenseCategory> {
        val rs = collection.whereEqualTo("value", id).limit(1).get().await()


        if (rs != null && !rs.isEmpty) {
            rs.first().reference.set(newItem.copy(id = id)).await()

            return Result.success(
                newItem.copy(id = id)
            )
        }

        return Result.failure(exception = Exception("Not found item"))
    }

    override suspend fun delete(id: String): Result<ExpenseCategory?> {
        val rs = collection.whereEqualTo("id", id).limit(1).get().await()
        if (rs != null && !rs.isEmpty) {
            val item: ExpenseCategory = rs.first().toObject()

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
        if (snapshot != null) {
            value = snapshot.map {
                it.toObject()
            }
        } else if (error != null) {
            TODO("Should handle errors")
        }
    }

}
