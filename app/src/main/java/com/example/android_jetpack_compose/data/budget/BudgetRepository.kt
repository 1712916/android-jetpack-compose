package com.example.android_jetpack_compose.data.budget

import com.example.android_jetpack_compose.data.*
import com.example.android_jetpack_compose.data.user.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.firebase_util.*
import com.example.android_jetpack_compose.ui.setting_budget.view_model.*
import com.google.firebase.firestore.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.*

abstract class BudgetRepository : CRUDRepository<BudgetEntity, Any> {
}

data class ValueObject(val value: Long = 0)
class BudgetRepositoryImpl : BudgetRepository(), FirebaseUtil {
    private var collection =
        fireStore.collection(AppUser.getInstance().getEmail())
            .document("setting")
            .collection("budget")

    override suspend fun create(item: BudgetEntity): Result<BudgetEntity> {
        collection.document("month_budget")
            .set(ValueObject(item.month))
        collection.document("day_budget")
            .set(ValueObject(item.day))

        return Result.success(item)
    }

    override suspend fun read(id: Any): Result<BudgetEntity?> {
        val monthBudget =
            collection.document("month_budget").get().await().get("value")?.toString()?.toLong()
        val dayBudget =
            collection.document("day_budget").get().await().get("value")?.toString()?.toLong()

        return Result.success(
            BudgetEntity(
                monthBudget ?: 0,
                dayBudget ?: 0,
            )
        )
    }

    override suspend fun update(id: Any, newItem: BudgetEntity): Result<BudgetEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Any): Result<BudgetEntity?> {
        TODO("Not yet implemented")
    }
}
