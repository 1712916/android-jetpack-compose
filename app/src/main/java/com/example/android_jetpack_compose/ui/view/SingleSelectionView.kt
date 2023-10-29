package com.example.android_jetpack_compose.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> SingleSelectionHorizontalView(
    data: Iterable<T>,
    selected: T,
    onChange: (T) -> Unit,
    itemBuilder: @Composable (T, Boolean) -> Unit,
) {
    LazyRow {
        items(data.toList()) { it ->
            Box(modifier = Modifier.clickable {
                onChange(it)
            }) {
                itemBuilder(it, selected == it)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> SingleSelectionFlowView(
    data: Iterable<T>,
    selected: T?,
    onChange: (T) -> Unit,
    itemBuilder: @Composable (T, Boolean) -> Unit,
) {

    val dataList = data.toList()
    FlowRow(modifier = Modifier.padding(8.dp)) {
        repeat(data.count()) {
            val item = dataList[it]
            Box(modifier = Modifier.clickable {
                onChange(item)
            }) {
                itemBuilder(item, selected == item)
            }
        }
    }
}
