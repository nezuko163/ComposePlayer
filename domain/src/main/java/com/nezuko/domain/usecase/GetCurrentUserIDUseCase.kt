package com.nezuko.domain.usecase

import com.nezuko.domain.repository.AuthLoginRepository

class GetCurrentUserIDUseCase(private val impl: AuthLoginRepository) {
    suspend fun execute() = impl.getCurrentUserID()
}