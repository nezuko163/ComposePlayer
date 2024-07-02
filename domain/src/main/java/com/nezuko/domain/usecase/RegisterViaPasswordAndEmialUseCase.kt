package com.nezuko.domain.usecase

import com.nezuko.domain.repository.AuthLoginRepository

class RegisterViaPasswordAndEmialUseCase(private val impl: AuthLoginRepository) {
    suspend fun execute(email: String, password: String,
                        onAuthComplete: (String?) -> Unit
    ) {
        impl.registerViaPasswordAndEmail(email, password, onAuthComplete)
    }
}