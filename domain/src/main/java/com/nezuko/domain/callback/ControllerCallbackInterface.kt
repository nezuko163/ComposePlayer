package com.nezuko.domain.callback

import com.nezuko.domain.model.Audio
import com.nezuko.domain.model.PlaybackState

interface ControllerCallbackInterface {
    fun onMetadataChanged(metadata: Audio)
    fun onPlaybackStateChanged(state: PlaybackState)
    fun onQueueChanged(list: ArrayList<Audio>)
}
