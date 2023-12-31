package com.example.android_jetpack_compose.data.expense

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.*
import com.example.android_jetpack_compose.data.mapping_firebase_object.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.firebase_util.*
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.*
import java.text.*
import java.util.*

abstract class DailyExpenseRepository() :
    CRUDRepository<MoneyModel, String>,
    LiveDataList<MoneyModel>,
    ListRepository<MoneyModel>

class InputDailyExpenseRepositoryImpl(date: Date) : DailyExpenseRepository(), AppAuthFirebaseUtil {
    override val collection: CollectionReference =
        authCollection
            .document(SimpleDateFormat("MM-yyyy").format(date))
            .collection(SimpleDateFormat("dd-MM-yyyy").format(date))

    private val mappingObject: MappingFirebaseObject<MoneyModel, MoneySaveObject> =
        MappingSavingMoneyModel()

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
                    mappingObject.getting(document)
                )
            }

            return Result.success(list)
        }


        return Result.success(emptyList())
    }

    override suspend fun create(item: MoneyModel): Result<MoneyModel> {
        val ref = collection.document()
        val storageItem = item.copy(id = ref.id)
        ref.set(mappingObject.saving(storageItem)).addOnSuccessListener { }.addOnFailureListener {
            throw Exception("Could not create item")
        }

        return Result.success(storageItem)
    }

    override suspend fun read(id: String): Result<MoneyModel?> {
        val it = collection.document(id).get().await()

        if (it == null || !it.exists()) {
            throw Exception("Unable to get expense by id")
        }
        return Result.success(
            mappingObject.getting(it)
        )

    }

    override suspend fun update(id: String, newItem: MoneyModel): Result<MoneyModel> {
        val ref = collection.document(id)
        val storageItem = newItem.copy(id = ref.id)

        ref.set(storageItem.toSaveObject()).addOnFailureListener {
            throw Exception("Could not update item")
        }

        return Result.success(storageItem)
    }

    override suspend fun delete(id: String): Result<MoneyModel?> {
        val rs = collection.document(id).get().await()

        if (rs != null) {
            val item: MoneyModel = mappingObject.getting(rs)
            rs.reference.delete().await()

            return Result.success(
                item
            )
        }

        return Result.failure(Exception("Not found expense"))
    }

}
