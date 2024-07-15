package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class PreviousTrackUseCase(
    private val impl: PlayerRepository
) {
    fun execute() = impl.previousTrack()
}