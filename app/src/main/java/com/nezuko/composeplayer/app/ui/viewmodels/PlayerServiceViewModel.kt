package com.nezuko.composeplayer.app.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.nezuko.domain.callback.ConnectionCallbackInterface
import com.nezuko.domain.callback.ControllerCallbackInterface
import com.nezuko.domain.model.Audio
import com.nezuko.domain.model.PlaybackState
import com.nezuko.domain.usecase.AddQueueItemUseCase
import com.nezuko.domain.usecase.ClearQueueUseCase
import com.nezuko.domain.usecase.NextTrackUseCase
import com.nezuko.domain.usecase.OnServiceStartUseCase
import com.nezuko.domain.usecase.OnServiceStopUseCase
import com.nezuko.domain.usecase.PauseUseCase
import com.nezuko.domain.usecase.PlayOrPauseUseCase
import com.nezuko.domain.usecase.PlayUseCase
import com.nezuko.domain.usecase.PreviousTrackUseCase
import com.nezuko.domain.usecase.SeekToUseCase
import com.nezuko.domain.usecase.SetPlayerCallbacksUseCase
import com.nezuko.domain.usecase.SkipToQueueItemUseCase
import com.nezuko.domain.usecase.StopUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.log

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
    private val skipToQueueItemUseCase: SkipToQueueItemUseCase,
    private val addQueueItemUseCase: AddQueueItemUseCase,
    private val clearQueueUseCase: ClearQueueUseCase,
    private val playOrPauseUseCase: PlayOrPauseUseCase
    // add queue item,
    // set queue,
    //
) : ViewModel() {

    val controllerCallback = object : ControllerCallbackInterface {
        override fun onMetadataChanged(metadata: Audio) {
            updateAudio(metadata)
            updateQueueTrackId(metadata.queueId)
        }

        override fun onPlaybackStateChanged(state: PlaybackState) {
            updateState(state)

            updatePlaying(state.state == PlaybackState.State.PLAYING)
        }

        override fun onQueueChanged(list: ArrayList<Audio>) {

        }
    }

    val connectionCallback = object : ConnectionCallbackInterface {
        override fun onConnected() {
            Log.i(TAG, "onConnected: connected!!!")
        }
    }

    init {
        setPlayerCallbacksUseCase.execute(controllerCallback, connectionCallback)

    }


    private val _queue = MutableLiveData<ArrayList<Audio>>()
    val queue: LiveData<ArrayList<Audio>>
        get() = _queue

    fun setQueue(list: ArrayList<Audio>) {
        Log.i(TAG, "setQueue: $list")
        clearQueueUseCase.execute()
        list.forEachIndexed { index: Int, audio: Audio ->
            addQueueItemUseCase.execute(audio, index.toLong())
        }
        _queue.value = list
    }

    private val TAG = "PLAYER_VIEWMODEL"
    private val _audio = MutableLiveData<Audio>()
    val audio: LiveData<Audio>
        get() = _audio

    fun updateAudio(newAudio: Audio) {
        _audio.value = newAudio
    }

    private val _state = MutableLiveData<PlaybackState>()
    val state: LiveData<PlaybackState>
        get() = _state

    fun updateState(newState: PlaybackState) {
        _state.value = newState
    }

    private val _isPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean>
        get() = _isPlaying


    fun updatePlaying(value: Boolean) {
        _isPlaying.value = value
    }


    private val _currentQueueTrackId = MutableLiveData<Long>()
    val currentQueueTrackId: LiveData<Long>
        get() = _currentQueueTrackId

    fun updateQueueTrackId(id: Long) {
        if (id == _currentQueueTrackId.value) return
        _currentQueueTrackId.value = id
        skipToQueueItem(id)
        Log.i(TAG, "updateQueueTrackId: ${currentQueueTrackId.value}")
    }

    fun onStart() {
        onServiceStartUseCase.execute()
    }

    fun onStop() = onServiceStopUseCase.execute()

    fun play() = play.execute()
    fun pause() = pause.execute()
    fun stop() = stop.execute()
    fun previousTrack() = previous.execute()
    fun nextTrack() = next.execute()
    fun seekTo(ms: Long) = seekTo.execute(ms)
    fun skipToQueueItem(id: Long) = skipToQueueItemUseCase.execute(id)
    fun playOrPause() = playOrPauseUseCase.execute()
}

object PlayerServiceViewModelStoreOwner : ViewModelStoreOwner {
    private val _viewModelStore = ViewModelStore()

    override val viewModelStore: ViewModelStore
        get() = _viewModelStore
}