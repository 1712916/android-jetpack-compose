package com.example.android_jetpack_compose.ui.login.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.*
import com.example.android_jetpack_compose.ui.login.view_model.*
import com.example.android_jetpack_compose.ui.view.*

@Composable
fun ForgotPasswordPage(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = viewModel()
) {
    val emailState by viewModel.emailStateFlow.collectAsState()
    val loadingState by viewModel.loadingStateFlow.collectAsState()
    val sendRequestSuccessState by viewModel.sendRequestSuccessState.collectAsState()
    val colorScheme = MaterialTheme.colorScheme
    val textTheme = MaterialTheme.typography
    
    BaseScreen(topBar = {
        AppBar(navController = navController, title = "Forgot Password", showBackButton = true)
    }) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            if (sendRequestSuccessState)
                Box(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                        .background(color = Color(0xFF4CAF50))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        "Check this email to reset your password",
                        style = textTheme.bodyMedium.copy(
                            color = Color.White
                        )
                    )
                }
            InputFieldView(
                title = "Email",
                text = emailState.text,
                message = emailState.invalidMessage,
                onValueChange = {
                    viewModel.setEmail(it)
                }
            )
            HeightBox(height = 20.0)
            LoadingButton(
                isLoading = loadingState,
                onClick = {
                    viewModel.onRequestResetPassword()
                }) {
                Text("Send")
            }
        }
    }
}
