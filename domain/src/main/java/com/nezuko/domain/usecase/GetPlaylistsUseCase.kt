package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlaylistRepository

class GetPlaylistsUseCase(
    private val impl: PlaylistRepository
) {
    suspend fun execute() = impl.getPlaylists()
}