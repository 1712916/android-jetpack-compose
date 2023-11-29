package com.example.android_jetpack_compose.ui.login.view_model

sealed class LoginEvent {
    data object Success : LoginEvent()
}
