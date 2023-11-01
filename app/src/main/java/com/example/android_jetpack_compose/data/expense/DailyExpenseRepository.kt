package com.example.android_jetpack_compose.data.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_jetpack_compose.data.CRUDRepository
import com.example.android_jetpack_compose.entity.MoneyModel
import java.util.Date

abstract class DailyExpenseRepository : CRUDRepository<MoneyModel, Long>() {
    abstract fun getExpensesByDate(date: Date): List<MoneyModel>?

    abstract fun getList(): LiveData<List<MoneyModel>>
}

class DailyExpenseRepositoryImpl : DailyExpenseRepository() {

    companion object {
        var id: Long = 0
        private val expenseList: MutableLiveData<List<MoneyModel>> by lazy {
            MutableLiveData<List<MoneyModel>>(arrayListOf())
        }
    }

    override fun getList(): LiveData<List<MoneyModel>> {
        return expenseList
    }

    override fun create(item: MoneyModel) {
        expenseList.postValue(expenseList.value?.plus(item.copy(id = id++)))
    }

    override fun read(id: Long): MoneyModel? {
        return expenseList.value?.find { moneyModel -> moneyModel.id == id }
    }

    override fun update(id: Long, newItem: MoneyModel) {
        val index = expenseList.value?.indexOfFirst { moneyModel -> moneyModel.id == newItem.id }

        if (index == -1) {
            throw IllegalStateException("Can not find expense id = $id")
        }

//        expenseList.value.toList().iu = newItem
    }

    override fun delete(id: Long): Boolean {
        return false
//        return expenseList.value.removeIf { it.id == id }
    }

    override fun getExpensesByDate(date: Date): List<MoneyModel>? {
        return expenseList.value?.filter { moneyModel -> moneyModel.createDate == date }
    }
}
