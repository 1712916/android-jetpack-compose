package com.example.android_jetpack_compose.ui.method.view

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
import com.example.android_jetpack_compose.data.category.*
import com.example.android_jetpack_compose.data.method.*
import com.example.android_jetpack_compose.data.share_data.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.ui.dashboard.*
import com.example.android_jetpack_compose.ui.dashboard.view.*
import com.example.android_jetpack_compose.ui.expense.view_model.*
import com.example.android_jetpack_compose.ui.method.view_model.*
import com.example.android_jetpack_compose.ui.view.*
import java.text.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MethodScreen(navController: NavController) {
    val viewModel: MethodViewModel = viewModel()
    val inputState = viewModel.inputState.collectAsState()
    val methods = CategoryAndMethodData.instance().methodListLiveData.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel
            .toastState
            .collect { message ->
                message?.show(context = context)
            }
    }
    Scaffold(
        topBar = {
            AppBar(
                navController,
                title = "Method Screen",
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
                        viewModel.addMethod()
                    }) {
                        Text(text = "Save")
                    }
                }
            }
            item {
                Row {
                    Box(modifier = Modifier.weight(1f)) {
                        SingleSelectionFlowView<ExpenseMethod>(
                            data = methods.value ?: emptyList(),
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
                                        Text(item.value)
                                    })
                                    WidthBox(width = 8.0)
                                }
                            })
                    }
                }
            }
        }
    }
}
