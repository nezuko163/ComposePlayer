package com.nezuko.domain.repository

import com.nezuko.domain.model.TrackInfo

interface PlayerRepository {
    fun play()
    fun pause()
    fun stop()
    fun nextTrack()
    fun previousTrack()
//    fun getCurrentTrackInfo(): TrackInfo
    fun adjustVolume(volume: Int)
    fun seekTo(position: Long)
}