package com.example.android_jetpack_compose.data.chart

import com.example.android_jetpack_compose.data.*
import com.example.android_jetpack_compose.data.expense.*
import com.example.android_jetpack_compose.entity.*
import kotlinx.coroutines.*
import org.threeten.bp.*
import java.util.*

class ChartRepository(val date: Date) : ListRepository<MoneyModel> {
    override suspend fun getList(): Result<List<MoneyModel>> {
        val days = mutableListOf<Date>()
        val calendar = Calendar.getInstance() // this takes current date

        calendar[Calendar.DAY_OF_MONTH] = 1
        val date = LocalDate.of(date.year, date.month, date.day)
        val lengthOfMonth = date.lengthOfMonth()

        while (days.count() < lengthOfMonth - 1) {
            days.add(calendar.time)
            calendar.add(Calendar.DATE, 1);
        }

        val deferredResults: List<Deferred<List<MoneyModel>>> = days.map { day ->
            GlobalScope.async {
                val i : InputDailyExpenseRepositoryImpl = InputDailyExpenseRepositoryImpl(day)
                i.getList().getOrNull() ?: listOf<MoneyModel>()
            }
        }
            val results : MutableList<MoneyModel> = mutableListOf()
          val listDates = deferredResults.awaitAll()
        listDates.forEach {
            results.addAll(it)
        }
        return  Result.success(results)
    }

    suspend fun getCategories(): Map<String, Long> {
        val list = getList().getOrDefault(listOf())
        return  list.groupBy { it.expenseCategory?.name ?: "" }
            .mapValues { it -> it.value.map { it.money } }
            .mapValues { it.value.sum() }
    }
}
