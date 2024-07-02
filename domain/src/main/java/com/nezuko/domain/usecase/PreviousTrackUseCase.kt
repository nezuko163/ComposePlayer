package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class PreviousTrackUseCase(val playerRepository: PlayerRepository) {
    fun execute() {
        playerRepository.previousTrack()
    }
}