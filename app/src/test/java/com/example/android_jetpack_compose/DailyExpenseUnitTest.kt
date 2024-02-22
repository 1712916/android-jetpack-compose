//package com.example.android_jetpack_compose
//
//import com.example.android_jetpack_compose.data.expense.DailyExpenseRepositoryImpl
//import com.example.android_jetpack_compose.entity.ExpenseCategory
//import com.example.android_jetpack_compose.entity.ExpenseMethod
//import com.example.android_jetpack_compose.entity.MoneyModel
//import com.example.android_jetpack_compose.ui.daily_expense.view_model.DailyExpenseViewModel
//import org.junit.Assert.assertEquals
//import org.junit.Test
//import java.util.Date
//
//class DailyExpenseUnitTest {
//    @Test
//    fun add_newExpense() {
//        val dailyExpenseViewModel: DailyExpenseViewModel = DailyExpenseViewModel(
//            DailyExpenseRepositoryImpl()
//        )
//        val addItem = MoneyModel(
//            expenseMethod = ExpenseMethod.Cash(),
//            money = 35000,
//            note = "Bun bo",
//            expenseCategory = ExpenseCategory.Expense(name = "An Toi", id = 1),
//            id = 1,
//            createDate = Date(),
//            updateDate = Date(),
//        )
//
//        dailyExpenseViewModel.add(addItem)
//
//
//
//        assertEquals(addItem, dailyExpenseViewModel.dailyExpenseStateFlow.value.expenses.first())
//    }
//
//    @Test
//    fun mutable_list() {
//        val numbers: MutableList<Int> = arrayListOf()
//
//        numbers.plus(1)
//        numbers.add(1)
//
//        print(numbers.toString())
//
//    }
//}
