package com.example.android_jetpack_compose.ui.login.view_model

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.login.*
import com.example.android_jetpack_compose.util.*
import com.example.android_jetpack_compose.util.text_field.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

sealed class LoginEvent {
    data object Success : LoginEvent()
}

open class LoginViewModel : BaseViewModel() {
    val repository: AuthRepository = AuthRepositoryImpl()

    private val _emailStateFlow = MutableStateFlow(TextFieldState())
    private val _passwordStateFlow = MutableStateFlow(TextFieldState())
    val _loginEvent = MutableSharedFlow<LoginEvent>()
    val _errorMessageStateFlow = MutableStateFlow<String?>(null)
    val _loadingStateFlow = MutableStateFlow<Boolean>(false)

    val emailStateFlow = _emailStateFlow.asStateFlow()
    val passwordStateFlow = _passwordStateFlow.asStateFlow()
    val loginEvent = _loginEvent.asSharedFlow()
    val errorMessageStateFlow = _errorMessageStateFlow.asStateFlow()
    val loadingStateFlow = _loadingStateFlow.asStateFlow()

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
                _loginEvent.emit(LoginEvent.Success)
            }.onFailure {
                emitToast(FailureToastMessage(it.message ?: "Login Failed"))
                _errorMessageStateFlow.emit(it.message ?: "Login Failed")
            }
            _loadingStateFlow.emit(false)
        }
    }
}

class RegisterViewModel : LoginViewModel() {
    private val _confirmPasswordStateFlow = MutableStateFlow(TextFieldState())
    val confirmPasswordStateFlow = _confirmPasswordStateFlow.asStateFlow()

    fun setConfirmPassword(password: String) {
        _confirmPasswordStateFlow.value = _confirmPasswordStateFlow.value.copy(text = password)
    }

    override fun validate() {
        super.validate()
        _confirmPasswordStateFlow.value =
            _confirmPasswordStateFlow.value.copy(
                invalidMessage = passwordValidator.validate(
                    _confirmPasswordStateFlow.value
                )
            )

        if (!confirmPasswordStateFlow.value.text.equals(passwordStateFlow.value.text)) {
            _confirmPasswordStateFlow.value =
                _confirmPasswordStateFlow.value.copy(invalidMessage = "Confirm password not match with password")
        }
    }

    override fun isValid(): Boolean {
        return super.isValid() && _confirmPasswordStateFlow.value.invalidMessage.isNullOrEmpty()
    }

    fun onRegister() {
        validate()

        if (!isValid()) {
            return
        }

        _errorMessageStateFlow.value = null

        viewModelScope.launch {
            _loadingStateFlow.emit(true)
            repository.register(
                emailStateFlow.value.text.trim(), passwordStateFlow.value.text,
            ).onSuccess {
                _loginEvent.emit(LoginEvent.Success)
            }.onFailure {
                emitToast(FailureToastMessage(it.message ?: "Register Failed"))
                _errorMessageStateFlow.emit(it.message ?: "Register Failed")
            }
            _loadingStateFlow.emit(false)
        }
    }
}
