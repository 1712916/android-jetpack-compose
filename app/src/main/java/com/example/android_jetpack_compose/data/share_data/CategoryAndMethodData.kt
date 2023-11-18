package com.example.android_jetpack_compose.data.share_data

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.*
import com.example.android_jetpack_compose.data.category.*
import com.example.android_jetpack_compose.data.method.*
import com.example.android_jetpack_compose.entity.*

public class CategoryAndMethodData private constructor() {
    val categoryListLiveData: LiveData<List<ExpenseCategory>> =
        CategoryRepositoryImpl().getLiveDataList()
    val methodListLiveData: LiveData<List<ExpenseMethod>> = MethodRepositoryImpl().getLiveDataList()

    companion object {
        private val instance: CategoryAndMethodData = CategoryAndMethodData()
        fun instance(): CategoryAndMethodData {
            return instance
        }
    }
}
