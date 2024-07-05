package com.nezuko.domain.repository

interface AuthRepository {
    suspend fun registerViaEmailAndPassword(
        email: String,
        password: String,
        onRegisterComplete: (String) -> Unit
    ): Result<Boolean>

    suspend fun registerViaGoogle(
        email: String,
        onRegisterComplete: (String) -> Unit
    ): Result<Boolean>

    suspend fun loginViaEmailAndPassword(
        email: String,
        password: String,
        onLoginComplete: (String) -> Unit
    ): Result<Boolean>

    suspend fun loginViaGoogle(
        email: String,
        onLoginComplete: (String) -> Unit
    ): Result<Boolean>

    suspend fun getCurrentUserID(): Result<String>
}