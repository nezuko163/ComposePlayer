package com.nezuko.data.utils

import android.support.v4.media.session.PlaybackStateCompat
import com.nezuko.domain.model.PlaybackState

fun playbackStateCompatToPlaybackState(playbackStateCompat: PlaybackStateCompat): PlaybackState {

    val duration = playbackStateCompat.extras?.getLong("duration") ?: -1L

    return PlaybackState(
        playbackStateCompat.position,
        duration,
        when (playbackStateCompat.state) {
            PlaybackStateCompat.STATE_PLAYING -> PlaybackState.State.PLAYING
            PlaybackStateCompat.STATE_STOPPED -> PlaybackState.State.STOPPED
            PlaybackStateCompat.STATE_PAUSED -> PlaybackState.State.PAUSED
            PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS -> PlaybackState.State.SKIPPING_TO_PREVIOUS
            PlaybackStateCompat.STATE_SKIPPING_TO_NEXT -> PlaybackState.State.SKIPPING_TO_NEXT
            PlaybackStateCompat.STATE_SKIPPING_TO_QUEUE_ITEM -> PlaybackState.State.SKIPPING_TO
            else -> PlaybackState.State.NONE
        }
    )
}