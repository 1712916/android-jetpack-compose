package com.example.android_jetpack_compose.ui.daily_expense.view

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.data.category.*
import com.example.android_jetpack_compose.data.method.*
import com.example.android_jetpack_compose.entity.ExpenseCategory
import com.example.android_jetpack_compose.entity.ExpenseMethod
import com.example.android_jetpack_compose.ui.daily_expense.view_model.*
import com.example.android_jetpack_compose.ui.dashboard.HeightBox
import com.example.android_jetpack_compose.ui.dashboard.WidthBox
import com.example.android_jetpack_compose.ui.view.AppNumberBoard
import com.example.android_jetpack_compose.ui.view.SingleSelectionFlowView
import com.example.android_jetpack_compose.util.*
import java.util.*

fun Modifier.gesturesDisabled(disabled: Boolean = true) = if (disabled) {
    pointerInput(Unit) {
        awaitPointerEventScope {
            // we should wait for all new pointer events
            while (true) {
                awaitPointerEvent(pass = PointerEventPass.Initial).changes.forEach(
                    PointerInputChange::consume
                )
            }
        }
    }
} else {
    this
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InputDailyExpenseView(date: Date) {
    val scope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val viewModel: InputDailyExpenseViewModel =
        viewModel(factory = InputDailyExpenseViewModelFactory(date = date))
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel
            .toastState
            .collect { message ->
                message?.show(context = context)

                if (message is SuccessToastMessage) {
                    appNavController!!.popBackStack()
                }
            }
    }
    val validateState by viewModel.validateState.collectAsState()
    val focusManager = LocalFocusManager.current

    ModalBottomSheetLayout(sheetState = bottomSheetState, sheetContent = {
        Text("BottomSheet")
        Text("BottomSheet")
        Text("BottomSheet")
        Text("BottomSheet")
        Text("BottomSheet")
        Text("BottomSheet")
        Text("BottomSheet")
        Text("BottomSheet")
        Text("BottomSheet")
    }) {
        Scaffold(modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }) { p ->
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
                                number = uiState.money ?: "", validateState = InputMoneyValidator(
                                    source = "",
                                    destination = uiState.money ?: "",
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
                                    value = uiState.note ?: "",
                                    onValueChange = {
                                        viewModel.changeNote(it)
                                    },
                                    modifier = Modifier.fillMaxWidth(),
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
                                        SingleSelectionFlowView<ExpenseCategory>(
                                            data = categories,
                                            selected = uiState.category,
                                            onChange = {
                                                viewModel.changeCategory(it)
                                            },
                                            itemBuilder = { item, isSelected ->
                                                Row {
                                                    FilterChip(onClick = {
                                                        viewModel.changeCategory(item)
                                                    }, selected = isSelected, leadingIcon = {
                                                        if (isSelected) Icon(
                                                            imageVector = Icons.Default.Check,
                                                            contentDescription = ""
                                                        )
                                                    }, content = {
                                                        Text(item.name)
                                                    })
                                                    WidthBox(width = 8.0)
                                                }
                                            })
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
                                    IconButton(onClick = {
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.List,
                                            contentDescription = "See All"
                                        )
                                    }
                                }
                                Row {
                                    Box(modifier = Modifier.weight(1f)) {
                                        SingleSelectionFlowView<ExpenseMethod>(
                                            data = methods,
                                            selected = uiState.method,
                                            onChange = {
                                                viewModel.changeMethod(it)
                                            },
                                            itemBuilder = { item, isSelected ->
                                                Row {
                                                    FilterChip(onClick = {
                                                        viewModel.changeMethod(item)
                                                    }, selected = isSelected, leadingIcon = {
                                                        if (isSelected) Icon(
                                                            imageVector = Icons.Default.Check,
                                                            contentDescription = ""
                                                        )
                                                    }, content = {
                                                        Text(item.name)
                                                    })
                                                    WidthBox(width = 8.0)
                                                }
                                            })
                                    }
                                }
                            }
                        }
                    },
                )
                Button(
                    onClick = {
                        if (validateState) {
                            viewModel.onSave()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    enabled = validateState,
                ) {
                    Text("Save")
                }
                AppNumberBoard(
                    onChanged = { viewModel.changeMoney(it) },
                    text = uiState.money ?: ""
                )
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
