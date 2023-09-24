package com.example.android_jetpack_compose.data.expense

import com.example.android_jetpack_compose.data.CRUDRepository
import com.example.android_jetpack_compose.entity.MoneyModel
import java.util.Date

abstract class DailyExpenseRepository : CRUDRepository<MoneyModel, Long>() {
    abstract fun getExpensesByDate(date: Date): List<MoneyModel>
}

class DailyExpenseRepositoryImpl : DailyExpenseRepository() {
    //todo: prototype list
    companion object {
        var id: Long = 0
        var expenseList: MutableList<MoneyModel> = mutableListOf()
    }

    override fun create(item: MoneyModel) {
        expenseList.plus(item.copy(id = id++))

    }

    override fun read(id: Long): MoneyModel? {
        return expenseList.find { moneyModel -> moneyModel.id == id }
    }

    override fun update(id: Long, newItem: MoneyModel) {
        val index = expenseList.indexOfFirst { moneyModel -> moneyModel.id == newItem.id }

        if (index == -1) {
            throw IllegalStateException("Can not find expense id = $id")
        }

        expenseList[index] = newItem
    }

    override fun delete(id: Long): Boolean {
        return expenseList.removeIf { it.id == id }
    }

    override fun getExpensesByDate(date: Date): List<MoneyModel> {
        return expenseList.filter { moneyModel -> moneyModel.createDate == date }
    }
}
