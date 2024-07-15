package com.nezuko.domain.repository

interface AuthRepository {
    suspend fun registerViaEmailAndPassword(
        email: String,
        password: String,
        onRegisterComplete: (String) -> Unit,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
    )

    suspend fun registerViaGoogle(
        email: String,
        onRegisterComplete: (String) -> Unit,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
    ): Result<Boolean>

    suspend fun loginViaEmailAndPassword(
        email: String,
        password: String,
        onLoginComplete: (String) -> Unit,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
    )

    suspend fun loginViaGoogle(
        email: String,
        onLoginComplete: (String) -> Unit,
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
    ): Result<Boolean>

    suspend fun getCurrentUserID(): Result<String>

    fun initializeApp()
}