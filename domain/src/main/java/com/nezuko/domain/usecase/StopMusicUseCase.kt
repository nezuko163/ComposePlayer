package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class StopMusicUseCase(val playerRepository: PlayerRepository) {
    fun execute() {
        playerRepository.stop()
    }
}