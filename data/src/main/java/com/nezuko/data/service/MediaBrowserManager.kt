package com.nezuko.data.service

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.widget.Toast
import com.nezuko.data.utils.metadataToAudio
import com.nezuko.data.utils.playbackStateCompatToPlaybackState
import com.nezuko.data.utils.queueItemToAudio
import com.nezuko.data.utils.queueToAudioList
import com.nezuko.domain.callback.ConnectionCallbackInterface
import com.nezuko.domain.callback.ControllerCallbackInterface
import com.nezuko.domain.model.Audio
import com.nezuko.domain.model.PlaybackState


class MediaBrowserManager(
    val context: Context
) {

    val TAG = "MEDDIA_MANAGER"
    lateinit var mediaBrowserCompat: MediaBrowserCompat
    lateinit var mediaControllerCompat: MediaControllerCompat

    lateinit var connectionRepository: ConnectionCallbackInterface
    lateinit var controllerRepository: ControllerCallbackInterface


    private val connectionCallback = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            super.onConnected()

            mediaControllerCompat = MediaControllerCompat(context, mediaBrowserCompat.sessionToken)
            Log.i(
                "MAIN_ACTIVITY",
                "onConnected: mediaController = ${::mediaControllerCompat.isInitialized}"
            )
            mediaControllerCompat.registerCallback(controllerCallback)
            Log.i(
                TAG,
                "onConnected: mediaCOntroller - init = ${::mediaControllerCompat.isInitialized}"
            )
            Toast.makeText(
                context,
                "mediaCOntroller - init = ${::mediaControllerCompat.isInitialized}",
                Toast.LENGTH_SHORT
            ).show()

            connectionRepository.onConnected()
        }

        override fun onConnectionFailed() {
            super.onConnectionFailed()

            Log.i(TAG, "onConnectionFailed: 123")
        }

        override fun onConnectionSuspended() {
            super.onConnectionSuspended()

            Log.i(TAG, "onConnectionSuspended: 321")
        }
    }

    private val controllerCallback = object : MediaControllerCompat.Callback() {
        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)

            if (metadata == null) return
            Log.i(TAG, "onMetadataChanged: ${metadataToAudio(metadata)}")
            Log.i(TAG, "onMetadataChanged: is initilized: ${::controllerRepository.isInitialized}")
            controllerRepository.onMetadataChanged(metadataToAudio(metadata))

        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)

            if (state == null) return
            Log.i(TAG, "onPlaybackStateChanged: ${playbackStateCompatToPlaybackState(state)}")
            controllerRepository.onPlaybackStateChanged(playbackStateCompatToPlaybackState(state))
        }

        override fun onAudioInfoChanged(info: MediaControllerCompat.PlaybackInfo?) {
            super.onAudioInfoChanged(info)

            if (info != null) {
                Log.i(TAG, "onAudioInfoChanged: ${info.volumeControl}")
            }
        }

        override fun onQueueChanged(queue: MutableList<MediaSessionCompat.QueueItem>?) {
            super.onQueueChanged(queue)

            if (queue == null) return
            controllerRepository.onQueueChanged(queueToAudioList(queue))
        }

    }

    fun onStart() {
        if (!::mediaBrowserCompat.isInitialized) {
            Log.i(TAG, "onStart: 123")
            mediaBrowserCompat = MediaBrowserCompat(
                context,
                ComponentName(context, MediaPlaybackService::class.java),  // 创建callback
                connectionCallback,
                null
            )
            mediaBrowserCompat.connect()
        } else {
            Log.i(TAG, "onStart: not connected")
        }
    }

    fun onStop() {
        if (::mediaControllerCompat.isInitialized) {
            Log.i(TAG, "onStop: manager on stop")
            mediaController()?.stop()
            mediaControllerCompat.unregisterCallback(controllerCallback)
        }
    }

    fun mediaController(): MediaControllerCompat.TransportControls? {
        if (::mediaControllerCompat.isInitialized) {
            return mediaControllerCompat.transportControls
        }
        return null
    }
}