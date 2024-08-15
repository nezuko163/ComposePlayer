package com.nezuko.domain.usecase

import com.nezuko.domain.repository.AuthRepository

class DisableOnlineModeUseCase(
    private val impl: AuthRepository
) {
    operator fun invoke() = impl.disableOnlineMode()
}