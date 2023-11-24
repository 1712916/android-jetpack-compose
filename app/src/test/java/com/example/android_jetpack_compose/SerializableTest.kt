//package com.example.android_jetpack_compose
//
//import com.example.android_jetpack_compose.entity.ExpenseCategory
//import com.example.android_jetpack_compose.entity.ExpenseMethod
//import com.example.android_jetpack_compose.entity.MoneyModel
//import kotlinx.serialization.encodeToString
//import kotlinx.serialization.json.Json
//import org.junit.Assert.*
//import org.junit.Test
//import java.util.Date
//
//class SerializableTest {
//    @Test
//    fun toJson() {
//        val timeStamp = 1699106168170
//        val expected =
//            """{"id":1,"money":35000,"note":null,"expenseCategory":{"type":1,"id":1,"name":"ăn sáng"},"expenseMethod":{"name":"Tien mat","id":0},"createDate":"$timeStamp","updateDate":"1699106168170"}"""
//
//        val actual = Json.encodeToString(
//            MoneyModel(
//                id = 1,
//                money = 35000,
//                note = null,
//                expenseCategory = ExpenseCategory.Expense(id = 1, name = "ăn sáng"),
//                expenseMethod = ExpenseMethod(name = "Tien mat", id = 0),
//                createDate = Date(timeStamp),
//                updateDate = Date(timeStamp),
//            ),
//        )
//
//        assertEquals(expected, actual)
//    }
//
//    @Test
//    fun fromJson() {
//        val timeStamp = 1699106168170
//        val expected =
//            MoneyModel(
//                id = 1,
//                money = 35000,
//                note = null,
//                expenseCategory = ExpenseCategory.Expense(id = 1, name = "ăn sáng"),
//                expenseMethod = ExpenseMethod(name = "Tien mat", id = 0),
//                createDate = Date(timeStamp),
//                updateDate = Date(timeStamp),
//            )
//
//        val json = Json.encodeToString(
//            MoneyModel(
//                id = 1,
//                money = 35000,
//                note = null,
//                expenseCategory = ExpenseCategory.Expense(id = 1, name = "ăn sáng"),
//                expenseMethod = ExpenseMethod(name = "Tien mat", id = 0),
//                createDate = Date(timeStamp),
//                updateDate = Date(timeStamp),
//            ),
//        )
//
//        val actual = Json.decodeFromString<MoneyModel>(json)
//
//        assertEquals(expected, actual)
//    }
//}
