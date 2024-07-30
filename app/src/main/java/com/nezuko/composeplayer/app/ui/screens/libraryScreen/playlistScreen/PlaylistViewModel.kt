package com.nezuko.composeplayer.app.ui.screens.libraryScreen.playlistScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.domain.model.Audio
import com.nezuko.domain.model.Playlist
import com.nezuko.domain.usecase.GetTrackListByPlaylistIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val getTrackListByPlaylistIdUseCase: GetTrackListByPlaylistIdUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val TAG = "PLAYLIST_VIEW_MODEL"

    private val _playlist = MutableLiveData<Playlist>()
    val playlist: LiveData<Playlist>
        get() = _playlist

    private val _trackList = MutableLiveData<ArrayList<Audio>>()
    val trackList: LiveData<ArrayList<Audio>>
        get() = _trackList

    fun setPlaylist(playlistModel: Playlist) {
        _playlist.postValue(playlistModel)
    }

    fun findPlaylist(id: Long) {
        val a = viewModelScope.launch(ioDispatcher) {
            val res = getTrackListByPlaylistIdUseCase.execute(id)
            if (res.isSuccess) {
                _trackList.postValue(res.getOrDefault(arrayListOf()))
            }

            Log.i(TAG, "findPlaylist: $res")
        }
    }
}