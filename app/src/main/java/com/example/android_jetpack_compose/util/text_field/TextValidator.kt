package com.example.android_jetpack_compose.util.text_field

interface TextValidator<T> {
    fun validate(input: T): String?
}

class EmailValidator : TextValidator<TextFieldState> {
    override fun validate(input: TextFieldState): String? {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(input.text).matches()) {
            return "Email is invalid"
        }
        return null
    }
}

class PasswordValidator : TextValidator<TextFieldState> {
    override fun validate(input: TextFieldState): String? {
        if (input.text.length < 6) {
            return "Password must be at least 6 characters long"
        }
        return null
    }
}
