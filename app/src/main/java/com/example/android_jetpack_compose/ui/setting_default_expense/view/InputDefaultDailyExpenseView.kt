@file:OptIn(ExperimentalMaterialApi::class)

package com.example.android_jetpack_compose.ui.setting_default_expense.view

import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.*
import androidx.compose.ui.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.data.share_data.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.ui.daily_expense.view.*
import com.example.android_jetpack_compose.ui.daily_expense.view_model.*
import com.example.android_jetpack_compose.ui.dashboard.*
import com.example.android_jetpack_compose.ui.dashboard.view.*
import com.example.android_jetpack_compose.ui.setting_default_expense.view_model.*
import com.example.android_jetpack_compose.ui.view.*
import com.example.android_jetpack_compose.util.*

@Composable
fun InputDefaultDailyExpenseView(navController: NavController) {
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val viewModel: SettingDefaultInputExpenseViewModel =
        viewModel(factory = DailyExpenseViewModelFactory(null))
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val categories = CategoryAndMethodData.instance().categoryListLiveData.observeAsState()
    val methods = CategoryAndMethodData.instance().methodListLiveData.observeAsState()
    val lazyState = rememberLazyListState()
    val validateState by viewModel.validateState.collectAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        Log.i("LaunchedEffect", "V Test")
    }

    LaunchedEffect(Unit) {
        snapshotFlow { lazyState.isScrollInProgress }
            .collect {
                focusManager.clearFocus()
            }
    }

    LaunchedEffect(Unit) {
        viewModel
            .toastState
            .collect { message ->
                message?.show(context = context)

                if (message is SuccessAndBackToastMessage) {
                    navController.popBackStack()
                }
            }
    }

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
                    state = lazyState,
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
                            SuggestMoneyInputView(uiState.money ?: "", output = {
                                viewModel.changeMoney(it)
                            })
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
                                    androidx.compose.material3.IconButton(onClick = {
                                        navController.navigate(
                                            ManagementCategoryExpense.route
                                        )
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "See All"
                                        )
                                    }
                                }
                                Row {
                                    Box(modifier = Modifier.weight(1f)) {
                                        SingleSelectionFlowView<ExpenseCategory>(
                                            data = categories.value ?: emptyList(),
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
                                    androidx.compose.material3.IconButton(onClick = {
                                        navController.navigate(
                                            ManagementMethodExpense.route
                                        )
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
                                            data = methods.value ?: emptyList(),
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
                Row() {
                    WidthBox(width = 16.0)

                    Button(
                        onClick = {
                            if (validateState) {
                                viewModel.onSave()
                            }
                        },
                        modifier = Modifier
                            .weight(1f),
                        enabled = validateState,
                    ) {
                        Text("Save")
                    }
                    WidthBox(width = 8.0)
                    Button(
                        onClick = {
                            if (validateState) {
                                viewModel.onSave(saveAnBack = false)
                            }
                        },
                        modifier = Modifier
                            .weight(1f),
                        enabled = validateState,
                    ) {
                        Text("Save & Continue")
                    }
                    WidthBox(width = 16.0)
                }
                AppNumberBoard(
                    onChanged = { viewModel.changeMoney(it) },
                    text = uiState.money ?: ""
                )
            }
        }
    }
}
