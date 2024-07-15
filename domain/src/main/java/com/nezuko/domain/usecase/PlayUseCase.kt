package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class PlayUseCase(
    private val impl: PlayerRepository
) {
    fun execute() = impl.play()
}