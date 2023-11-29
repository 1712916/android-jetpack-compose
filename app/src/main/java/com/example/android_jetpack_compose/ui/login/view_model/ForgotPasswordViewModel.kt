package com.example.android_jetpack_compose.ui.login.view_model

import androidx.lifecycle.*
import com.example.android_jetpack_compose.data.login.*
import com.example.android_jetpack_compose.util.*
import com.example.android_jetpack_compose.util.text_field.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class ForgotPasswordViewModel : BaseViewModel() {
    private val repository: AuthRepository = AuthRepositoryImpl()

    private val _emailStateFlow = MutableStateFlow(TextFieldState())
    val emailStateFlow = _emailStateFlow.asStateFlow()

    private val _sendRequestSuccessStateFlow = MutableStateFlow(false)
    val sendRequestSuccessState = _sendRequestSuccessStateFlow.asStateFlow()

    private val emailValidator: TextValidator<TextFieldState> = EmailValidator()

    fun setEmail(email: String) {
        _emailStateFlow.update {
            it.copy(text = email)
        }
    }

    fun validate() {
        _emailStateFlow.update {
            it.copy(invalidMessage = emailValidator.validate(it))
        }

    }

    private fun isValid(): Boolean {
        return _emailStateFlow.value.invalidMessage == null
    }

    fun onRequestResetPassword() {
        validate()

        if (!isValid()) {
            return
        }
        
        viewModelScope.launch {
            showLoading()
            repository.onRequestNewPassword(emailStateFlow.value.text).onSuccess {
                _sendRequestSuccessStateFlow.emit(true)
            }.onFailure {
                emitToast(FailureToastMessage(it.message ?: "Login Failed"))

            }
            hideLoading()
        }
    }
}
