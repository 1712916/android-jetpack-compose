package com.example.android_jetpack_compose.ui.daily_expense

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*

import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_jetpack_compose.ui.dashboard.HeightBox
import com.example.android_jetpack_compose.ui.dashboard.WidthBox
import com.example.android_jetpack_compose.ui.view.*
import kotlinx.coroutines.launch

@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InputDailyExpenseView() {
    var text by remember { mutableStateOf("Hello") }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Text("BottomSheet")
            Text("BottomSheet")
            Text("BottomSheet")
            Text("BottomSheet")
            Text("BottomSheet")
            Text("BottomSheet")
            Text("BottomSheet")
            Text("BottomSheet")
            Text("BottomSheet")
        }
    ) {
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
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                    ) {
                                    Text("Categories")
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "See All"
                                        )
                                    }
                                }
                                Row {
                                    Box(modifier = Modifier.weight(1f)) {
                                        MultipleSelectionFlowView<Int>(
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
                                }
                            }
                        }
                        item {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Methods")
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Default.List,
                                            contentDescription = "See All"
                                        )
                                    }
                                }
                                Row {
                                    Box(modifier = Modifier.weight(1f)) {
                                        MultipleSelectionFlowView<Int>(
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
                                }
                            }
                        }
                    },
                )
                Button(
                    onClick = {
                        scope.launch {
                            scope.launch {
                                bottomSheetState.show()
                            }
                        }
                    },
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
