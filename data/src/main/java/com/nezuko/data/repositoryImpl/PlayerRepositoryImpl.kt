package com.nezuko.data.repositoryImpl

import android.support.v4.media.session.MediaControllerCompat.TransportControls
import com.nezuko.domain.repository.PlayerRepository

class PlayerRepositoryImpl(private val transportControls: TransportControls) : PlayerRepository {
    override fun play() {
        transportControls.play()
    }

    override fun pause() {
        transportControls.pause()
    }

    override fun stop() {
        transportControls.stop()
    }

    override fun nextTrack() {
        transportControls.skipToNext()
    }

    override fun previousTrack() {
        transportControls.skipToPrevious()
    }

    override fun adjustVolume(volume: Int) {

    }

    override fun seekTo(position: Long) {
        transportControls.seekTo(position)
    }
}