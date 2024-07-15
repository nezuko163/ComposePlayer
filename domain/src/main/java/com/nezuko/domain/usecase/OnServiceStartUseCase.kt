package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class OnServiceStartUseCase(
    private val impl: PlayerRepository
) {
    fun execute() = impl.onServiceStart()
}