package com.nezuko.domain.usecase

import com.nezuko.domain.repository.AuthRepository

class LoginViaEmailAndPasswordUseCase(
    private val impl: AuthRepository
) {
    suspend fun execute(email: String, password: String, onLoginComplete: (String) -> Unit) =
        impl.loginViaEmailAndPassword(email, password, onLoginComplete)
}