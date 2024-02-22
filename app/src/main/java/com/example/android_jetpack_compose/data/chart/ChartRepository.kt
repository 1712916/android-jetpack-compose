package com.example.android_jetpack_compose.data.chart

import com.example.android_jetpack_compose.data.*
import com.example.android_jetpack_compose.data.expense.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import kotlinx.datetime.*

class ChartRepository(val date: LocalDate) : ListRepository<MoneyModel> {
    override suspend fun getList(): Result<List<MoneyModel>> {
        val days = GetMonthDate(date).getDates()

        val deferredResults: List<Deferred<List<MoneyModel>>> = days.map { day ->
            GlobalScope.async {
                val i: InputDailyExpenseRepositoryImpl = InputDailyExpenseRepositoryImpl(day)
                i.getList().getOrNull() ?: listOf<MoneyModel>()
            }
        }
        val results: MutableList<MoneyModel> = mutableListOf()
        val listDates = deferredResults.awaitAll()
        listDates.forEach {
            results.addAll(it)
        }
        return Result.success(results)
    }

    suspend fun getCategories(): Map<String, Long> {
        val list = getList().getOrDefault(listOf())
        return list.groupBy { it.category?.value ?: "" }
            .mapValues { it -> it.value.map { it.money } }
            .mapValues { it.value.sum() }
    }
}
