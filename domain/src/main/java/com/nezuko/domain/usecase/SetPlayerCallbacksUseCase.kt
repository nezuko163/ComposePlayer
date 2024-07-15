package com.nezuko.domain.usecase

import com.nezuko.domain.callback.ConnectionCallbackInterface
import com.nezuko.domain.callback.ControllerCallbackInterface
import com.nezuko.domain.repository.PlayerRepository

class SetPlayerCallbacksUseCase(
    private val impl: PlayerRepository
) {
    fun execute(
        controllerCallback: ControllerCallbackInterface,
        connectionCallback: ConnectionCallbackInterface
    ) = impl.setCallbacks(controllerCallback, connectionCallback)
}