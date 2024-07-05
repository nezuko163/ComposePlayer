package com.nezuko.domain.usecase

import com.nezuko.domain.repository.AuthRepository

class RegisterViaEmailAndPasswordUseCase(
    private val impl: AuthRepository
) {
    suspend fun execute(email: String, password: String, onLoginComplete: (String) -> Unit) =
        impl.registerViaEmailAndPassword(email, password, onLoginComplete)
}