package com.nezuko.domain.usecase

import com.nezuko.domain.repository.AuthRepository

class RegisterViaGoogleUseCase(
    private val impl: AuthRepository
) {
    suspend fun execute(email: String, onLoginComplete: (String) -> Unit) =
        impl.registerViaGoogle(email, onLoginComplete)
}