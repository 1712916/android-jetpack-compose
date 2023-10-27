package com.example.android_jetpack_compose.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
