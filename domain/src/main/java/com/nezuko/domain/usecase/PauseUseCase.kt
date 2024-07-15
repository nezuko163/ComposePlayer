package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class PauseUseCase(
    private val impl: PlayerRepository
) {
    fun execute() = impl.pause()
}