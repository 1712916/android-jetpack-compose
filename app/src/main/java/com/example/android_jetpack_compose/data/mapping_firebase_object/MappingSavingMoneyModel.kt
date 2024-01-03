package com.example.android_jetpack_compose.data.mapping_firebase_object

import com.example.android_jetpack_compose.data.category.*
import com.example.android_jetpack_compose.data.method.*
import com.example.android_jetpack_compose.entity.*
import com.google.firebase.firestore.*
import java.util.*

class MappingSavingMoneyModel : MappingFirebaseObject<MoneyModel, MoneySaveObject>() {
    private val categoryRepository: CategoryRepository = CategoryRepositoryImpl()
    private val methodRepository: MethodRepository = MethodRepositoryImpl()

    override fun saving(sourceObject: MoneyModel): MoneySaveObject {
        return sourceObject.toSaveObject()
    }

    override suspend fun getting(sourceObject: DocumentSnapshot): MoneyModel {
        return MoneyModel(
            id = sourceObject["id"].toString(),
            money = if (sourceObject["money"] != null) sourceObject["money"] as Long else 0,
            note = sourceObject["note"]?.toString(),
            createDate = sourceObject.getDate("createDate"),
            updateDate = sourceObject.getDate("updateDate"),
            category = categoryRepository.read(sourceObject["category"].toString()).getOrNull(),
            method = methodRepository.read(sourceObject["method"].toString()).getOrNull(),
        )
    }
}

data class MoneySaveObject(
    val id: String = "",
    val money: Long = 0,
    val note: String? = null,
    val category: String? = null,
    val method: String? = null,
    val createDate: Date? = null,
    val updateDate: Date? = null,
) {
    fun toMoneyModel(): MoneyModel {

        return MoneyModel(
            id = id,
            money = money,
            note = note,
            createDate = createDate,
            updateDate = updateDate,
            method = null,
            category = null,
        )
    }
}

fun MoneyModel.toSaveObject(): MoneySaveObject {
    return MoneySaveObject(
        id = id,
        money = money,
        note = note,
        category = category?.id,
        method = method?.id,
        createDate = createDate,
        updateDate = updateDate,
    );
}
