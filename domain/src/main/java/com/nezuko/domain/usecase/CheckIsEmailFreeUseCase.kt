package com.nezuko.domain.usecase

import com.nezuko.domain.repository.AuthRepository
import com.sun.net.httpserver.Authenticator.Failure
import com.sun.net.httpserver.Authenticator.Success

class CheckIsEmailFreeUseCase(
    private val impl: AuthRepository
) {
    suspend fun execute(
        email: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) = impl.isEmailFree(
        email = email,
        onSuccess = onSuccess,
        onFailure = onFailure
    )
}