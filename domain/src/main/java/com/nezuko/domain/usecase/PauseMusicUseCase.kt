package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class PauseMusicUseCase(val playerRepository: PlayerRepository) {
    fun execute() {
        playerRepository.pause()
    }
}