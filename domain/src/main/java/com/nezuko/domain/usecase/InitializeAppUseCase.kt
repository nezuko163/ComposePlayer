package com.nezuko.domain.usecase

import com.nezuko.domain.repository.AuthRepository

class InitializeAppUseCase(
    private val impl: AuthRepository
) {
    fun execute() = impl.initializeApp()
}