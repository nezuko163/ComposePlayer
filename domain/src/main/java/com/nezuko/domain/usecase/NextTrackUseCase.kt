package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class NextTrackUseCase(val playerRepository: PlayerRepository) {
    fun execute() {
        playerRepository.nextTrack()
    }
}