package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlaylistRepository

class GetAllLocalTracksUseCase(
    private val impl: PlaylistRepository
) {
    fun execute() = impl.getAllLocalTracks()
}