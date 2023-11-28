package com.example.android_jetpack_compose.ui.chart.view_model

import android.util.*
import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.chart.*
import com.example.android_jetpack_compose.util.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

class MonthChartViewModel : BaseViewModel() {
    val _uiState = MutableStateFlow(mapOf<String, Long>())
    val uiState: StateFlow<Map<String, Long>> = _uiState.asStateFlow()
    val chartRepository: ChartRepository = ChartRepository(Date())
    init {
      viewModelScope.launch {
//          chartRepository.getList().onSuccess {
//              _uiState.value = it
//          }

          chartRepository.getCategories().let {
              Log.i("LOL", it.toString())
              _uiState.value = it
          }
      }
    }
}
