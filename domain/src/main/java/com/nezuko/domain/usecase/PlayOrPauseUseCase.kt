package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class PlayOrPauseUseCase(
    private val impl: PlayerRepository
) {
    fun execute() = impl.playOrPause()
}