package com.nezuko.domain.repository

interface AuthLoginRepository {

    suspend fun loginViaGoogle()

    suspend fun loginViaPasswordAndEmail()

    suspend fun registerViaGoogle()

    suspend fun registerViaPasswordAndEmail(email: String, password: String,
                                            onRegisterComplete: (String?) -> Unit
    )

    suspend fun getCurrentUserID(): Result<String>
}