package com.nezuko.domain.model

import kotlin.time.Duration

data class PlaybackState(
    var position: Long = -1L,
    var duration: Long = -1L,
    var state: State = State.NONE
) {
    enum class State() {
        PLAYING,
        PAUSED,
        STOPPED,
        SKIPPING_TO_NEXT,
        SKIPPING_TO_PREVIOUS,
        SKIPPING_TO,
        NONE,
    }
}