package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class StopUseCase(
    private val impl: PlayerRepository
) {
    fun execute() = impl.stop()
}