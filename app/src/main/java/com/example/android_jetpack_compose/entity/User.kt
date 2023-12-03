package com.example.android_jetpack_compose.entity

import com.example.android_jetpack_compose.util.*

data class User(val email: String)
class AppUser private constructor() {
    private var user: User? = null

    lateinit var preferencesManager: PreferencesManager

    private object Holder {
        val INSTANCE = AppUser()
    }

    companion object {
        @JvmStatic
        fun getInstance(): AppUser {
            return Holder.INSTANCE
        }
    }

    fun getEmail(): String {
        return user!!.email
    }

    fun setUser(user: User) {
        this.user = user
        preferencesManager.saveData(preferencesManager.currentEmailKey(), user.email)
    }

    fun clear() {
        user = null
        preferencesManager.remove(preferencesManager.currentEmailKey())
    }

    fun isLogin(): Boolean {
        val email = preferencesManager.getData(preferencesManager.currentEmailKey())
        val isLogin = !email.isNullOrEmpty()
        if (isLogin) {
            setUser(User(email!!))
        }
        return isLogin
    }
}
