package com.example.android_jetpack_compose.ui.login.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.*
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
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "Login",
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
                HeightBox(height = 20.0)
                LoadingButton(
                    isLoading = loadingState,
                    onClick = {
                        viewModel.onLogin()
                    }) {
                    Text("Login")
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        "Forget password?",
                        modifier = Modifier
                            .clickable {
                                navController.navigate(ForgotPassword.route)
                            },
                        style = textTheme.labelMedium.copy(
                            color = colorScheme.secondary
                        )
                    )
                    WidthBox(width = 20.0)
                    Text(
                        "Register now!",
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Register.route) {
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

@Composable
fun InputFieldView(
    title: String,
    text: String,
    onValueChange: (String) -> Unit,
    message: String? = null,
    onKeyEvent: ((KeyEvent) -> Boolean)? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isVisibleText: Boolean = true,
    toggleVisibleText: Boolean = false,
) {
    val toggleVisibleState = remember {
        mutableStateOf(isVisibleText)
    }
    val modifier = Modifier
        .fillMaxWidth()
    if (onKeyEvent != null) {
        modifier.onKeyEvent(onKeyEvent)
    }
    Column {
        Text(title)
        TextField(
            modifier = modifier,
            value = text,
            isError = !message.isNullOrEmpty(),
            singleLine = true,
            onValueChange = onValueChange,
            keyboardActions = keyboardActions,
            visualTransformation = if (isVisibleText || toggleVisibleState.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (toggleVisibleText)
                    IconButton(onClick = {
                        toggleVisibleState.value = !toggleVisibleState.value
                    }) {
                        Icon(
                            imageVector = if (toggleVisibleState.value) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = ""
                        )
                    }
            },
        )
        InvalidMessageView(message)
    }
}
