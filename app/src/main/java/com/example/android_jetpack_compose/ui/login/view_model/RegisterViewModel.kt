package com.example.android_jetpack_compose.ui.login.view_model

import androidx.lifecycle.*
import com.example.android_jetpack_compose.util.*
import com.example.android_jetpack_compose.util.text_field.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

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
