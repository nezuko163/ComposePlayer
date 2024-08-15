package com.nezuko.domain.usecase

import com.nezuko.domain.repository.AuthRepository

class EnableOnlineModeUseCase(
    private val impl: AuthRepository
) {
    operator fun invoke() = impl.enableOnlineMode()
}