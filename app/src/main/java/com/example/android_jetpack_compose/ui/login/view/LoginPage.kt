package com.example.android_jetpack_compose.ui.login.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.ui.login.view_model.*
import com.example.android_jetpack_compose.ui.view.*

@Preview
@Composable
fun LoginPagePreview() {
    LoginPage(rememberNavController())
}
@Composable
fun LoginPage(navController: NavController, viewModel: LoginViewModel = viewModel()) {
    val emailState by viewModel.emailStateFlow.collectAsState()
    val passwordState by viewModel.passwordStateFlow.collectAsState()
    val errorMessageState by viewModel.errorMessageStateFlow.collectAsState()
    val loadingState by viewModel.loadingStateFlow.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val colorScheme = MaterialTheme.colorScheme
    val textTheme = MaterialTheme.typography

    LaunchedEffect(Unit) {
        viewModel.loginEvent.collect {
            when (it) {
                LoginEvent.Success -> navController.navigate(Main.route) {
                    popUpTo(0)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toastState.collect {
            it?.show(context = context)
        }
    }

    BaseScreen(
        body = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Login")
                Box {
                    HeightBox(height = 100.0)
                    InvalidMessageView(errorMessageState)
                }

                Text("Email")
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onKeyEvent {
                            if (it.nativeKeyEvent.keyCode == NativeKeyEvent.KEYCODE_ENTER) {
                                focusManager.moveFocus(FocusDirection.Next)
                                true
                            }
                            false
                        },
                    value = emailState.text,
                    isError = !emailState.invalidMessage.isNullOrEmpty(),
                    singleLine = true,
                    onValueChange = {
                        viewModel.setEmail(it)
                    },
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                )
                InvalidMessageView(emailState.invalidMessage)
                Text("Password")
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onKeyEvent {
                            if (it.nativeKeyEvent.keyCode == NativeKeyEvent.KEYCODE_ENTER) {
                                focusManager.clearFocus(true)
                                viewModel.onLogin()
                            }
                            false
                        },
                    value = passwordState.text,
                    isError = !passwordState.invalidMessage.isNullOrEmpty(),
                    singleLine = true,
                    onValueChange = {
                        viewModel.setPassword(it)
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus(true)
                            viewModel.onLogin()
                        }
                    ),
                )
                InvalidMessageView(passwordState.invalidMessage)

                Text("Forget password")
                HeightBox(height = 20.0)
                LoadingButton(
                    isLoading = loadingState,
                    onClick = {
                        viewModel.onLogin()
                    }) {
                    Text("Login")
                }
            }
        }
    )
}
@Composable
private fun InvalidMessageView(
    message: String?
) {
    val colorScheme = MaterialTheme.colorScheme
    val textTheme = MaterialTheme.typography
    Box {
        HeightBox(height = 20.0)
        if (message != null)
            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
                    .background(color = colorScheme.errorContainer)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    message!!,
                    style = textTheme.labelSmall.copy(
                        color = colorScheme.error
                    )
                )
            }
    }
}
