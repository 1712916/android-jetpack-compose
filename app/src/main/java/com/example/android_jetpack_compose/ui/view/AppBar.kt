package com.example.android_jetpack_compose.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.*
import androidx.navigation.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavController,
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    showBackButton: Boolean = false
) {
    // TopAppBar Composable
    TopAppBar(
        // Provide Title
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
            )
        },
        actions = actions,
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        }
    )
}
