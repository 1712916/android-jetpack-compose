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
fun <T> MultipleSelectionHorizontalView(
    data: Iterable<T>,
    selectedList: Iterable<T>,
    onChange: (List<T>) -> Unit,
    itemBuilder:@Composable (T, Boolean) -> Unit,
) {
    val selectedSet = selectedList.toSet()

    LazyRow(content = {
        items(data.toList()) { it ->
            Box(modifier = Modifier.clickable {
                onChange(
                    selectedSet.toMutableList().apply {
                        if (selectedSet.contains(it)) {
                            remove(it)
                        } else {
                            add(it)
                        }
                    }.toList(),
                )
            }) {
                itemBuilder(it, selectedSet.contains(it))
            }
        }
    })
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> MultipleSelectionFlowView(
    data: Iterable<T>,
    selectedList: Iterable<T>,
    onChange: (List<T>) -> Unit,
    itemBuilder:@Composable (T, Boolean) -> Unit,
) {
    val selectedSet = selectedList.toSet()
    val dataList = data.toList()
    FlowRow(modifier = Modifier.padding(8.dp)) {
        repeat(data.count()) {
            val item = dataList[it]
            Box(modifier = Modifier.clickable {
                onChange(
                    selectedSet.toMutableList().apply {
                        if (selectedSet.contains(item)) {
                            remove(item)
                        } else {
                            add(item)
                        }
                    }.toList(),
                )
            }) {
                itemBuilder(item, selectedSet.contains(item))
            }
        }
    }
}
