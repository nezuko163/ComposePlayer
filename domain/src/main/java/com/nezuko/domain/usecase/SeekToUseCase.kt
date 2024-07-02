package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class SeekToUseCase(val playerRepository: PlayerRepository) {
    fun execute(ms: Long) {
        playerRepository.seekTo(ms)
    }
}