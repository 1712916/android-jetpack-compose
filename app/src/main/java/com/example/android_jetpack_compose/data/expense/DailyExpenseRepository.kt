package com.example.android_jetpack_compose.data.expense

import androidx.lifecycle.LiveData
import com.example.android_jetpack_compose.data.*
import com.example.android_jetpack_compose.data.user.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.firebase_util.*
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.*
import java.text.*
import java.util.Date

abstract class DailyExpenseRepository() :
    CRUDRepository<MoneyModel, String>,
    LiveDataList<MoneyModel>,
    ListRepository<MoneyModel>

class InputDailyExpenseRepositoryImpl(date: Date) : DailyExpenseRepository(), FirebaseUtil {
    private var collection: CollectionReference =
        fireStore.collection(AppUser.getInstance().getEmail())
            .document(SimpleDateFormat("MM-yyyy").format(date))
            .collection(SimpleDateFormat("dd-MM-yyyy").format(date))

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
                val data = document.data!!
                list.add(
                    MoneyModel(
                        id = data.getValue("id") as String,
                        note = data.getValue("note") as String?,
                        money = data.getValue("money") as Long,
                        updateDate = document.getTimestamp("updateDate")?.toDate(),
                        createDate = document.getTimestamp("createDate")?.toDate(),
                        expenseCategory = ExpenseCategory(
                            id = ((data.getValue("expenseCategory") as Map<*, *>)["id"] as Long).toInt(),
                            name = (data.getValue("expenseCategory") as Map<*, *>)["name"] as String
                        ),
                        expenseMethod = ExpenseMethod(
                            id = ((data.getValue("expenseMethod") as Map<*, *>)["id"] as Long).toInt(),
                            name = (data.getValue("expenseMethod") as Map<*, *>)["name"] as String
                        ),
                    )
                )
            }

            return Result.success(list)
        }


        return Result.success(emptyList())
    }

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
        val data = it.data!!

        return Result.success(
            MoneyModel(
                id = data.getValue("id") as String,
                note = data.getValue("note") as String?,
                expenseCategory = ExpenseCategory(
                    id = ((data.getValue("expenseCategory") as Map<*, *>)["id"] as Long).toInt(),
                    name = (data.getValue("expenseCategory") as Map<*, *>)["name"] as String
                ),
                money = data.getValue("money") as Long,
                updateDate = Date(),
                createDate = Date(),
                expenseMethod = ExpenseMethod(
                    id = ((data.getValue("expenseMethod") as Map<*, *>)["id"] as Long).toInt(),
                    name = (data.getValue("expenseMethod") as Map<*, *>)["name"] as String
                ),
            )
        )

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
            val data = rs.first().data
            val item = MoneyModel(
                id = data.getValue("id") as String,
                note = data.getValue("note") as String?,
                expenseCategory = ExpenseCategory(
                    id = ((data.getValue("expenseCategory") as Map<*, *>)["id"] as Long).toInt(),
                    name = (data.getValue("expenseCategory") as Map<*, *>)["name"] as String
                ),
                money = data.getValue("money") as Long,
                updateDate = Date(),
                createDate = Date(),
                expenseMethod = ExpenseMethod(
                    id = ((data.getValue("expenseMethod") as Map<*, *>)["id"] as Long).toInt(),
                    name = (data.getValue("expenseMethod") as Map<*, *>)["name"] as String
                ),
            )

            rs.first().reference.delete().await()

            return Result.success(
                item
            )
        }



        return Result.failure(Exception("Not found expense"))
    }

}
