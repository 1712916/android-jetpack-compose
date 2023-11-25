package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.data.budget.*
import com.example.android_jetpack_compose.data.user.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.firebase_util.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.tasks.*
import org.threeten.bp.*
import java.text.*
import java.time.format.*
import java.util.*

abstract class MonthExpenseRepository(val date: Date) : ProgressExpenseRepository
class MonthExpenseRepositoryImpl(date: Date) : MonthExpenseRepository(date), FirebaseUtil {
    private val budgetRepository: BudgetRepository = BudgetRepositoryImpl()
    private var document =
        fireStore.collection(AppUser.getInstance().getEmail())
            .document(SimpleDateFormat("MM-yyyy").format(date))

    override suspend fun getDateExpenses(): List<DateExpense> {
        val days = mutableListOf<Date>()
        val calendar = Calendar.getInstance() // this takes current date

        calendar[Calendar.DAY_OF_MONTH] = 1
        val date = LocalDate.of(2010, 1, 19)
        val lengthOfMonth = date.lengthOfMonth()

        while (days.count() < lengthOfMonth - 1) {
            days.add(calendar.time)
            calendar.add(Calendar.DATE, 1);
        }
        val getDateExpenseRepository: GetDateExpenseRepository = GetDateExpenseRepositoryImpl()


        return days.map { day -> getDateExpenseRepository.getExpense(day) }
    }

    override suspend fun getTotalExpense(): Long {
        return getDateExpenses().fold(0) { sum, element -> sum + element.money }
    }

    override suspend fun getProgressData(): WeekTrackerInfoModel {
        //list of total expense each date
        val expenses = getDateExpenses()
        val totalSpend = getTotalExpense()
        //get list of period week
        //        val previousWeekExpenseRepository: WeekExpenseRepository =
        //            WeekExpenseRepositoryImpl(Date(date.time - 7 * 86400 * 1000))
        //        val totalPeriodSpend = previousWeekExpenseRepository.getTotalExpense()
        //        val differentExpenseUtil: DifferentExpenseUtil = DifferentExpenseUtil(
        //            totalPeriodSpend,
        //            totalSpend
        //        )
        val budget = budgetRepository.read("").getOrNull()


        return WeekTrackerInfoModel(
            totalSpend = totalSpend,
            //            differenceNumber = differentExpenseUtil.differenceNumber(),
            //            differentEnum = differentExpenseUtil.differentEnum(),
            weekTackers = expenses.map { dateData ->
                WeekTrackerModel(
                    date = dateData.date,
                    dateSpend = dateData.money,
                )
            }.toTypedArray(),
            budget = budget?.month ?: 0,
        )
    }
}
