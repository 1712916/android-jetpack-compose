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
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.*
import com.example.android_jetpack_compose.*
import com.example.android_jetpack_compose.ui.login.view_model.*
import com.example.android_jetpack_compose.ui.view.*

@Composable
fun RegisterPage(navController: NavController, viewModel: RegisterViewModel = viewModel()) {
    val emailState by viewModel.emailStateFlow.collectAsState()
    val passwordState by viewModel.passwordStateFlow.collectAsState()
    val confirmPasswordState by viewModel.confirmPasswordStateFlow.collectAsState()
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

                else -> {}
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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text(
                    "Register",
                    style = textTheme.displayMedium
                )
                Box {
                    HeightBox(height = 100.0)
                    InvalidMessageView(errorMessageState)
                }

                InputFieldView(
                    title = "Email",
                    text = emailState.text,
                    onValueChange = {
                        viewModel.setEmail(it)
                    },
                    message = emailState.invalidMessage,
                    onKeyEvent = {
                        if (it.nativeKeyEvent.keyCode == NativeKeyEvent.KEYCODE_ENTER) {
                            focusManager.moveFocus(FocusDirection.Next)

                        }
                        false
                    },
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.moveFocus(FocusDirection.Next) }
                    ),
                )
                InputFieldView(
                    title = "Password",
                    text = passwordState.text,
                    onValueChange = {
                        viewModel.setPassword(it)
                    },
                    message = passwordState.invalidMessage,
                    onKeyEvent = {
                        if (it.nativeKeyEvent.keyCode == NativeKeyEvent.KEYCODE_ENTER) {
                            focusManager.clearFocus(true)
                            viewModel.onLogin()
                        }
                        false
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus(true)
                            viewModel.onLogin()
                        }
                    ),
                    isVisibleText = false,
                    toggleVisibleText = true,
                )
                InputFieldView(
                    title = "Confirm Password",
                    text = confirmPasswordState.text,
                    onValueChange = {
                        viewModel.setConfirmPassword(it)
                    },
                    message = confirmPasswordState.invalidMessage,
                    onKeyEvent = {
                        if (it.nativeKeyEvent.keyCode == NativeKeyEvent.KEYCODE_ENTER) {
                            focusManager.clearFocus(true)
                            viewModel.onRegister()
                        }
                        false
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus(true)
                            viewModel.onRegister()
                        }
                    ),
                    isVisibleText = false,
                    toggleVisibleText = true,
                )
                HeightBox(height = 20.0)
                LoadingButton(
                    isLoading = loadingState,
                    onClick = {
                        viewModel.onRegister()
                    }) {
                    Text("Register")
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        "Login now!",
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Login.route) {
                                    popUpTo(0)
                                }
                            },
                        style = textTheme.labelMedium.copy(
                            color = colorScheme.primary
                        )
                    )
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
                    message,
                    style = textTheme.labelSmall.copy(
                        color = colorScheme.error
                    )
                )
            }
    }
}
