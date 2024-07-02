package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlaylistsRepository

class GetAllPlaylistsFromLocalUseCase(private val playlistsRepository: PlaylistsRepository) {
    fun execute() =
        playlistsRepository.getAllPlaylistsFromLocal()
}