package com.nezuko.composeplayer.app.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nezuko.domain.callback.ConnectionCallbackInterface
import com.nezuko.domain.callback.ControllerCallbackInterface
import com.nezuko.domain.model.Audio
import com.nezuko.domain.model.PlaybackState
import com.nezuko.domain.usecase.NextTrackUseCase
import com.nezuko.domain.usecase.OnServiceStartUseCase
import com.nezuko.domain.usecase.OnServiceStopUseCase
import com.nezuko.domain.usecase.PauseUseCase
import com.nezuko.domain.usecase.PlayUseCase
import com.nezuko.domain.usecase.PreviousTrackUseCase
import com.nezuko.domain.usecase.SeekToUseCase
import com.nezuko.domain.usecase.SetPlayerCallbacksUseCase
import com.nezuko.domain.usecase.SkipToQueueItemUseCase
import com.nezuko.domain.usecase.StopUseCase

class PlayerServiceViewModel(
    private val play: PlayUseCase,
    private val pause: PauseUseCase,
    private val stop: StopUseCase,
    private val previous: PreviousTrackUseCase,
    private val next: NextTrackUseCase,
    private val seekTo: SeekToUseCase,
    private val onServiceStartUseCase: OnServiceStartUseCase,
    private val onServiceStopUseCase: OnServiceStopUseCase,
    private val setPlayerCallbacksUseCase: SetPlayerCallbacksUseCase,
    private val skipToQueueItemUseCase: SkipToQueueItemUseCase
    // add queue item,
    // set queue,
    //
) : ViewModel() {

    init {
        val controllerCallback = object : ControllerCallbackInterface {
            override fun onMetadataChanged(metadata: Audio) {
                _audio.value = metadata
            }

            override fun onPlaybackStateChanged(state: PlaybackState) {
                _state.value = state
            }

            override fun onQueueChanged(list: ArrayList<Audio>) {
                setQueue(list)
            }
        }

        val connectionCallback = object : ConnectionCallbackInterface {
            override fun onConnected() {
                Log.i(TAG, "onConnected: connected!!!")
            }
        }

        setPlayerCallbacksUseCase.execute(controllerCallback, connectionCallback)
    }


    private val _queue = MutableLiveData<ArrayList<Audio>>()
    val queue: LiveData<ArrayList<Audio>>
        get() = _queue

    fun setQueue(list: ArrayList<Audio>) {
        _queue.value = list
    }

    private val TAG = "PLAYER_VIEWMODEL"
    private val _audio = MutableLiveData<Audio>()
    val audio: LiveData<Audio>
        get() = _audio

    private val _state = MutableLiveData<PlaybackState>()
    val state: LiveData<PlaybackState>
        get() = _state

    fun onStart() = onServiceStartUseCase.execute()
    fun onStop() = onServiceStopUseCase.execute()

    fun play() = play.execute()
    fun pause() = pause.execute()
    fun stop() = stop.execute()
    fun previousTrack() = previous.execute()
    fun nextTrack() = next.execute()
    fun seekTo(ms: Long) = seekTo.execute(ms)
    fun skipToQueueItem(id: Long) = skipToQueueItemUseCase.execute(id)
}