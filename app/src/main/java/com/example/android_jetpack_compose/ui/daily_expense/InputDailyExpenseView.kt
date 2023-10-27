package com.example.android_jetpack_compose.ui.daily_expense

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_jetpack_compose.ui.dashboard.HeightBox
import com.example.android_jetpack_compose.ui.dashboard.WidthBox
import com.example.android_jetpack_compose.ui.view.AppNumberBoard
import com.example.android_jetpack_compose.ui.view.MultipleSelectionHorizontalView

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InputDailyExpenseView() {
    var text by remember { mutableStateOf("Hello") }

    Scaffold { p ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(p),
//            verticalArrangement =    Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                content = {
                    item {
                        MoneyInputView(
                            number = text,
                            validateState = InputMoneyValidator(
                                source = "",
                                destination = text
                            ).validate()
                        )
                    }
                    item {
                        HeightBox(height = 16.0)
                    }
                    item {
                        Column {
                            Text("Note:")
                            HeightBox(height = 8.0)
                            OutlinedTextField(
                                value = text, onValueChange = { text = it },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    item {
                        HeightBox(height = 16.0)
                    }
                    item {
                        Column(modifier = Modifier.padding(vertical = 16.dp)) {
                            Text("Categories")
                            Row {
                                LazyRow(
                                    modifier = Modifier.weight(1f),
                                    content = {
                                        item {
                                            FilterChip(
                                                onClick = { },
                                                selected = false,
                                                content = {
                                                    Icon(
                                                        imageVector = Icons.Default.AccountBox,
                                                        contentDescription = ""
                                                    )
                                                }
                                            )
                                            WidthBox(width = 8.0)
                                        }
                                        items(count = 10) { index ->
                                            FilterChip(
                                                onClick = { },
                                                selected = false,
                                                leadingIcon = {
                                                    Icon(
                                                        imageVector = Icons.Default.AccountBox,
                                                        contentDescription = ""
                                                    )
                                                },
                                                content = {
                                                    Text(index.toString())
                                                }
                                            )
                                            WidthBox(width = 8.0)
                                        }
                                    })
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Default.List,
                                        contentDescription = "See All"
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Column {
                            Text("Methods")
                            Row {
                                Box(modifier = Modifier.weight(1f)) {
                                    MultipleSelectionHorizontalView<Int>(
                                        data = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
                                        selectedList = arrayListOf(2),
                                        onChange = {
                                                   print(it.toString())
                                        },
                                        itemBuilder = { item, isSelected ->
                                            Row {
                                                FilterChip(
                                                    onClick = { },
                                                    selected = isSelected,
                                                    leadingIcon = {
                                                        Icon(
                                                            imageVector = Icons.Default.AccountBox,
                                                            contentDescription = ""
                                                        )
                                                    },
                                                    content = {
                                                        Text(item.toString())
                                                    }
                                                )
                                                WidthBox(width = 8.0)
                                            }
                                        }
                                    )
                                }

                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Default.List,
                                        contentDescription = "See All"
                                    )
                                }
                            }
                        }
                    }
                },
            )
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Save")
            }
            AppNumberBoard(onChanged = { text = it }, text = text)
        }
    }
}

class InputMoneyValidator(private val source: String, private val destination: String) {
    fun validate(): InputValidateState {
        if (destination.isEmpty()) {
            return EmptyInputState
        }
        if (source == destination) {
            return SameInputState
        }
        return ValidInputState
    }
}
