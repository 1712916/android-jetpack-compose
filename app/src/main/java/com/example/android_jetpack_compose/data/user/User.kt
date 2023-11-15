package com.example.android_jetpack_compose.data.user

class User(val email: String) {}
class AppUser private constructor() {
    private var user: User? = User(email = "smile.vinhnt@gmail.com")

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
}
