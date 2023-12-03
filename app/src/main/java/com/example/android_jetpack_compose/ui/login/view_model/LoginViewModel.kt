package com.example.android_jetpack_compose.ui.login.view_model

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.login.*
import com.example.android_jetpack_compose.entity.*
import com.example.android_jetpack_compose.util.*
import com.example.android_jetpack_compose.util.text_field.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

open class
LoginViewModel : BaseViewModel() {
    val repository: AuthRepository = AuthRepositoryImpl()

    private val _emailStateFlow = MutableStateFlow(TextFieldState())
    private val _passwordStateFlow = MutableStateFlow(TextFieldState())
    val _loginEvent = MutableSharedFlow<LoginEvent>()
    val _errorMessageStateFlow = MutableStateFlow<String?>(null)

    val emailStateFlow = _emailStateFlow.asStateFlow()
    val passwordStateFlow = _passwordStateFlow.asStateFlow()
    val loginEvent = _loginEvent.asSharedFlow()
    val errorMessageStateFlow = _errorMessageStateFlow.asStateFlow()

    private val emailValidator: TextValidator<TextFieldState> = EmailValidator()
    val passwordValidator: TextValidator<TextFieldState> = PasswordValidator()
    private fun emailState(): TextFieldState {
        return _emailStateFlow.value
    }

    private fun passwordState(): TextFieldState {
        return _passwordStateFlow.value
    }

    fun setEmail(email: String) {
        _emailStateFlow.value = _emailStateFlow.value.copy(text = email)
    }

    fun setPassword(password: String) {
        _passwordStateFlow.value = _passwordStateFlow.value.copy(text = password)
    }

    open fun validate() {
        _emailStateFlow.value =
            emailState().copy(invalidMessage = emailValidator.validate(emailState()))

        _passwordStateFlow.value =
            passwordState().copy(invalidMessage = passwordValidator.validate(passwordState()))
    }

    open fun isValid(): Boolean {
        listOf(_emailStateFlow.value, _passwordStateFlow.value).forEach {
            if (it.invalidMessage != null) {
                return false
            }
        }

        return true
    }

    fun onLogin() {
        validate()

        if (!isValid()) {
            return
        }

        _errorMessageStateFlow.value = null

        viewModelScope.launch {
            _loadingStateFlow.emit(true)
            repository.login(
                _emailStateFlow.value.text.trim(), _passwordStateFlow.value.text,
            ).onSuccess {
                AppUser.getInstance().setUser(User(_emailStateFlow.value.text.trim()))
                _loginEvent.emit(LoginEvent.Success)
            }.onFailure {
                emitToast(FailureToastMessage(it.message ?: "Login Failed"))
                _errorMessageStateFlow.emit(it.message ?: "Login Failed")
            }
            _loadingStateFlow.emit(false)
        }
    }

    fun isLogin(): Boolean {
        return AppUser.getInstance().isLogin()
    }
}
