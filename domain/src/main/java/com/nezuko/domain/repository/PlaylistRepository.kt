package com.nezuko.domain.repository

import com.nezuko.domain.model.Audio
import com.nezuko.domain.model.Playlist

interface PlaylistRepository {
    fun getAllLocalTracks(): Playlist

    suspend fun getPlaylists(): ArrayList<Playlist>

    suspend fun getTrackListByPlaylistId(id: Long): Result<ArrayList<Audio>>
}