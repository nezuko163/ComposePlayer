package com.nezuko.domain.usecase

import com.nezuko.domain.repository.AuthRepository

class GetCurrentUserIDUseCase(
    private val impl: AuthRepository
) {
    suspend fun execute() = impl.getCurrentUserID()
}