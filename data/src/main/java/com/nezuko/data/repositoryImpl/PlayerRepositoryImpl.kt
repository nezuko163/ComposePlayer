package com.nezuko.data.repositoryImpl

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import com.nezuko.domain.callback.ConnectionCallbackInterface
import com.nezuko.domain.callback.ControllerCallbackInterface
import com.nezuko.data.service.MediaBrowserManager
import com.nezuko.data.utils.audioToMediaDescriptionCompat
import com.nezuko.domain.model.Audio
import com.nezuko.domain.repository.PlayerRepository

class PlayerRepositoryImpl(
    private val context: Context,
    private val activity: Activity
) : PlayerRepository {

    private val TAG = "PLAYER_REPOSITORY"
    private val mediaBrowser = MediaBrowserManager(activity)

    override fun setCallbacks(
        controllerCallbackInterface: ControllerCallbackInterface,
        connectionCallbackInterface: ConnectionCallbackInterface
    ) {
        mediaBrowser.controllerRepository = controllerCallbackInterface
        mediaBrowser.connectionRepository = connectionCallbackInterface
    }

    override fun skipToQueueItem(id: Long) {
        mediaBrowser.mediaController()?.skipToQueueItem(id)
    }

    override fun play() {
        mediaBrowser.mediaController()?.play()
    }

    override fun pause() {
        mediaBrowser.mediaController()?.pause()
    }

    override fun stop() {
        mediaBrowser.mediaController()?.stop()
    }

    override fun nextTrack() {
        mediaBrowser.mediaController()?.skipToNext()
    }

    override fun previousTrack() {
        mediaBrowser.mediaController()?.skipToPrevious()
    }

    override fun adjustVolume(volume: Int) {

    }

    override fun seekTo(position: Long) {
        mediaBrowser.mediaController()?.seekTo(position)
    }

    override fun addQueueItem(audio: Audio, id: Long?) {
        mediaBrowser.mediaControllerCompat.addQueueItem(audioToMediaDescriptionCompat(audio, id))
    }

    override fun setQueue(list: ArrayList<Audio>) {
        Log.i(TAG, "setQueue: ")
    }

    override fun onServiceStart() {
        mediaBrowser.onStart()
    }

    override fun onServiceStop() {
        mediaBrowser.onStop()
    }


}