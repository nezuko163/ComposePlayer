package com.nezuko.composeplayer.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.nezuko.data.service.MediaBrowserManager
import com.nezuko.domain.usecase.NextTrackUseCase
import com.nezuko.domain.usecase.PauseMusicUseCase
import com.nezuko.domain.usecase.PlayMusicUseCase
import com.nezuko.domain.usecase.PreviousTrackUseCase
import com.nezuko.domain.usecase.SeekToUseCase
import com.nezuko.domain.usecase.StopMusicUseCase

class PlayerServiceViewModel(
    private val play: PlayMusicUseCase,
    private val pause: PauseMusicUseCase,
    private val stop: StopMusicUseCase,
    private val previous: PreviousTrackUseCase,
    private val next: NextTrackUseCase,
    private val seekTo: SeekToUseCase,
    private val mediaBrowserManager: MediaBrowserManager
) : ViewModel() {

    fun play() {
        play.execute()
    }

    fun pause() {
        pause.execute()
    }

    fun stop() {
        stop.execute()
    }

    fun previous() {
        previous.execute()
    }

    fun next() {
        next.execute()
    }

    fun seekTo(ms: Long) {
        seekTo.execute(ms)
    }

    fun start() {
        mediaBrowserManager.onStart()
    }
}