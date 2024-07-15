package com.nezuko.domain.usecase

import com.nezuko.domain.model.Audio
import com.nezuko.domain.repository.PlayerRepository

class AddQueueItemUseCase(
    private val impl: PlayerRepository
) {
    fun execute(audio: Audio, queueId: Long) = impl.addQueueItem(audio, queueId)
}