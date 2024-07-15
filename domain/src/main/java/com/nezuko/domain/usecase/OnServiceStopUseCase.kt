package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlayerRepository

class OnServiceStopUseCase(
    private val impl: PlayerRepository
) {
    fun execute() = impl.onServiceStop()
}