package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class PlayMusicUseCase(val playerRepository: PlayerRepository) {
    fun execute() {
        playerRepository.play()
    }
}