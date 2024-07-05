package com.nezuko.domain.usecase

import com.nezuko.domain.repository.AuthRepository

class LoginViaGoogleUseCase(
    private val impl: AuthRepository
) {
    suspend fun execute(email: String, onLoginComplete: (String) -> Unit) =
        impl.loginViaGoogle(email, onLoginComplete)
}