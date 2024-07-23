package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class ClearQueueUseCase(
    private val impl: PlayerRepository
) {
    fun execute() = impl.clearQueue()
}