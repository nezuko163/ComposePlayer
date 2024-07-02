package com.nezuko.domain.usecase

import com.nezuko.domain.repository.PlaylistsRepository

class GetAllTracksFromLocalStoreUseCase(
    private val playlistsRepository: PlaylistsRepository
) {
    fun execute() = playlistsRepository.getAllTracksFromLocalStore()
}