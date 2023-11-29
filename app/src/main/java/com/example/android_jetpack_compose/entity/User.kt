package com.example.android_jetpack_compose.entity

data class User(val email: String)
class AppUser private constructor() {
    private var user: User? = null

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
    }

    fun clear() {
        user = null
    }
}
