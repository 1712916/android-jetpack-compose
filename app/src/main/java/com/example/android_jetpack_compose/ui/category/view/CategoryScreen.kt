package com.example.android_jetpack_compose.ui.expense.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.data.method.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.ui.dashboard.*
import com.example.android_jetpack_compose.ui.expense.view_model.*
import com.example.android_jetpack_compose.ui.method.view_model.*
import com.example.android_jetpack_compose.ui.view.*
import com.example.android_jetpack_compose.util.*
import java.text.*

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CategoryScreen(navController: NavController) {
    val viewModel: CategoryViewModel = viewModel()
    val inputState = viewModel.inputState.collectAsState()
    val expenseList = viewModel.categoryList.observeAsState()
    val context = LocalContext.current
    val openAlertDialog = remember { mutableStateOf(false) }
    val selectedCategory = remember { mutableStateOf<ExpenseCategory?>(null) }


    LaunchedEffect(Unit) {
        viewModel
            .toastState
            .collect { message ->
                message?.show(context = context)
            }
    }

    Box {
        Scaffold(
            topBar = {
                AppBar(
                    navController,
                    title = "Category Screen",
                    showBackButton = true,
                )
            },
        ) { it ->
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .padding(
                        horizontal = 16.dp
                    )
            ) {
                item {
                    Row {
                        OutlinedTextField(
                            value = inputState.value,
                            onValueChange = {
                                viewModel.updateInput(it)
                            },
                            modifier = Modifier.weight(1f),
                        )
                        TextButton(onClick = {
                            viewModel.addCategory()
                        }) {
                            Text(text = "Save")
                        }
                    }
                }
                item {
                    Row {
                        Box(modifier = Modifier.weight(1f)) {
                            SingleSelectionFlowView<ExpenseCategory>(
                                data = expenseList.value ?: emptyList<ExpenseCategory>(),
                                onChange = {},
                                selected = null,
                                itemBuilder = { item, isSelected ->
                                    Row {
                                        FilterChip(onClick = {
                                        }, selected = isSelected, leadingIcon = {
                                            if (isSelected) Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = ""
                                            )
                                        }, label = {
                                            Text(item.name)
                                        },
                                            trailingIcon = {
                                                IconButton(onClick = {
                                                    selectedCategory.value = item
                                                    openAlertDialog.value = true
                                                }) {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = "delete category"
                                                    )
                                                }
                                            }
                                        )
                                        WidthBox(width = 8.0)
                                    }
                                })
                        }
                    }
                }
            }
        }
        if (openAlertDialog.value)
            AlertDialogExample(
                dialogTitle = "Remove Category",
                dialogText = "Are you want to remove ${selectedCategory.value!!.name}",
                onConfirmation = {
                    openAlertDialog.value = false
                    viewModel.deleteCategory(selectedCategory.value!!)
                    selectedCategory.value = null
                },
                onDismissRequest = {
                    openAlertDialog.value = false
                    selectedCategory.value = null
                }
            )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}
