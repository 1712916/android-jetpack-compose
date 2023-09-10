package com.example.android_jetpack_compose.data.dashboard

import com.example.android_jetpack_compose.entity.DifferentEnum
import com.example.android_jetpack_compose.entity.WeekTrackerInfoModel
import com.example.android_jetpack_compose.entity.WeekTrackerModel
import java.util.Calendar
import java.util.Date

abstract class DashBoardRepository {
   abstract fun getWeekProgressData(date: Date) : WeekTrackerInfoModel
    protected abstract fun  getWeekExpenseByDate(date: Date) : List<DateExpense>
    fun getTotalExpense(expenses: List<DateExpense>): Double{
        return  expenses.fold(0.0){ sum, element -> sum + element.money }
    }
}
 class DifferentExpenseUtil(val preExpense: Double,val curExpense: Double) {
     fun differenceNumber(): Double {
         if (curExpense > 0) {
             return  preExpense/curExpense
         }
         return  1.0
     }

     fun differentEnum(): DifferentEnum {
        val differenceNumber = differenceNumber()
        return  if (differenceNumber.equals(1.0)) {
              DifferentEnum.BALANCE
        } else if (differenceNumber > 1.0) {
              DifferentEnum.INCREASE

        } else {
            DifferentEnum.DECREASE
        }
     }
 }

class DashBoardRepositoryImpl : DashBoardRepository() {
    override fun getWeekProgressData(date: Date): WeekTrackerInfoModel {
        //list of total expense each date
        val expenses = getWeekExpenseByDate(Calendar.getInstance().time)

        //get list of period week
        val periodWeekExpenses = getWeekExpenseByDate(Calendar.getInstance().time)

        val totalSpend = getTotalExpense(expenses);

        val totalPeriodSpend = getTotalExpense(periodWeekExpenses);

        val dateBudget : Double = GetDayBudgetRepository().getBudget()

        return WeekTrackerInfoModel(
            totalSpend = totalSpend,
            differenceNumber = DifferentExpenseUtil(totalPeriodSpend ,totalSpend).differenceNumber(),
            differentEnum = DifferentExpenseUtil(totalPeriodSpend ,totalSpend).differentEnum(),
            weekTackers = expenses.map { dateData -> WeekTrackerModel(
                date = dateData.date,
                 dateSpend = dateData.money,
                dateBudget = dateBudget,
            ) }.toTypedArray(),
        )
    }

    override fun  getWeekExpenseByDate(date: Date) : List<DateExpense> {
        //tính ra tuần hiện tại
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY

        val days = mutableListOf<Date>()

        for (i in 0..6) {
            days.add( calendar.time)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val getExpenseRepository: GetExpenseRepository = GetExpenseRepositoryImpl()

        return  days.map { day ->  getExpenseRepository.getExpense(day)}
    }
}

abstract class GetBudgetRepository {
    abstract  fun getBudget() : Double
}

class GetMonthBudgetRepository : GetBudgetRepository() {
    override fun getBudget(): Double {
        return  3000.0
    }
}

class GetDayBudgetRepository : GetBudgetRepository() {
    override fun getBudget(): Double {
        //tính budget của tháng
        val monthBudget = GetMonthBudgetRepository().getBudget()
        //tính số ngày trong tháng
        val numberDateOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)

        return monthBudget / numberDateOfMonth;
    }
}

abstract  class GetExpenseRepository {
    abstract fun getExpense(date: Date) : DateExpense
}

class GetExpenseRepositoryImpl : GetExpenseRepository() {
    override fun getExpense(date: Date): DateExpense {
        //todo: need to fix
        return  DateExpense(Calendar.getInstance().time, 200.0)
    }

}

data class DateExpense(val date: Date, val money: Double)

data class MoneyModel(
    val money: Double,
    val createDate: Date,
    val updateDate: Date,
    val expenseCategory: ExpenseCategory,
    val expenseMethod: ExpenseMethod,
)


sealed class ExpenseCategory : Category  {
    data class Income(override val name: String, override val id: Int) : ExpenseCategory()
    data class Expense(override val name: String, override val id: Int) : ExpenseCategory()
}

 interface Category {
    abstract  val name: String
    abstract val id: Int
}

fun main() {
    val income = ExpenseCategory.Income("salary", 1 )

    val category: Category = income
    print(category)
}


//tiền mặt
//thẻ (Thẻ thì có tên thẻ)
//khác (Người khác trả)
sealed class ExpenseMethod : Category {
    data class Cash(override val name: String = "Tien mat", override val id: Int = 0): ExpenseMethod()
    data class BankAccount(override val name: String, override val id: Int = 1): ExpenseMethod()
    data class Other(override val name: String, override val id: Int = 2): ExpenseMethod()
}
