package com.nezuko.domain.repository

import com.nezuko.domain.callback.ConnectionCallbackInterface
import com.nezuko.domain.callback.ControllerCallbackInterface
import com.nezuko.domain.model.Audio

interface PlayerRepository {
    fun play()
    fun pause()
    fun stop()
    fun nextTrack()
    fun previousTrack()
//    fun getCurrentTrackInfo(): TrackInfo
    fun adjustVolume(volume: Int)
    fun seekTo(position: Long)
    fun addQueueItem(audio: Audio, id: Long?)
    fun setQueue(list: ArrayList<Audio>)
    fun onServiceStart()
    fun onServiceStop()
    fun setCallbacks(
        controllerCallbackInterface: ControllerCallbackInterface,
        connectionCallbackInterface: ConnectionCallbackInterface
    )
    fun skipToQueueItem(id: Long)
    fun clearQueue()
    fun playOrPause()
}