package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlaylistRepository

class GetTrackListByPlaylistIdUseCase(
    private val impl: PlaylistRepository
) {
    suspend fun execute(id: Long) = impl.getTrackListByPlaylistId(id)
}