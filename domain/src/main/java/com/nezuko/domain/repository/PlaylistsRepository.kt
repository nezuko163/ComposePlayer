package com.nezuko.domain.repository

import com.nezuko.domain.model.PlaylistModel

interface PlaylistsRepository {
    fun getAllTracksFromLocalStore(): PlaylistModel
    fun getAllPlaylistsFromLocal(): ArrayList<PlaylistModel>
}