package com.example.android_jetpack_compose.data.login

import com.example.android_jetpack_compose.entity.*
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.*
import com.google.firebase.ktx.*
import kotlinx.coroutines.tasks.*

abstract class AuthRepository {
    abstract suspend fun login(email: String, password: String): Result<User>
    abstract suspend fun register(email: String, password: String): Result<User>
    abstract suspend fun logout()

    abstract suspend fun onRequestNewPassword(email: String): Result<Any>
}

class AuthRepositoryImpl : AuthRepository() {
    private val auth: FirebaseAuth = Firebase.auth
    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            if (result.user == null) {
                throw Exception("Login failed")
            }

            Result.success(User(email))
        } catch (e: Exception) {
            Result.failure(Exception("Login failure! Please check your email or password!"))
        }
    }

    override suspend fun register(email: String, password: String): Result<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            if (result.user == null) {
                throw Exception("Register failed")
            }

            Result.success(User(email))
        } catch (e: Exception) {
            Result.failure(e)
            //    Result.failure(Exception("Register failure! Please check your email or password!"))
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun onRequestNewPassword(email: String): Result<Any> {
        try {
            auth.sendPasswordResetEmail(email)
            return Result.success("")
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
