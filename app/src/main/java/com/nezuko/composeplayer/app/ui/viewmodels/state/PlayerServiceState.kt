package com.nezuko.composeplayer.app.ui.viewmodels.state

import com.nezuko.domain.model.Audio
import com.nezuko.domain.model.PlaybackState

data class PlayerServiceState(
    val audio: Audio = Audio(),
    val state: PlaybackState = PlaybackState(),
    val queue: ArrayList<Audio> = arrayListOf()
)
