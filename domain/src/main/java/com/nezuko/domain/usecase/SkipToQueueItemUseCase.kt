package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class SkipToQueueItemUseCase(
    private val impl: PlayerRepository
) {
    fun execute(id: Long) = impl.skipToQueueItem(id)
}