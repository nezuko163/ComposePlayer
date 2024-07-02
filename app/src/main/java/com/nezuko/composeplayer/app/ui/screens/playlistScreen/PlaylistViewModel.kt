package com.nezuko.composeplayer.app.ui.screens.playlistScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nezuko.domain.model.PlaylistModel
import com.nezuko.domain.usecase.GetAllTracksFromLocalStoreUseCase
import org.koin.androidx.compose.get

class PlaylistViewModel(
    private val getAllTracksFromLocalStoreUseCase: GetAllTracksFromLocalStoreUseCase
) : ViewModel() {
    private var _playlist = MutableLiveData<PlaylistModel>()
    val playlist: LiveData<PlaylistModel>
        get() = _playlist

    fun setPlaylist(playlistModel: PlaylistModel) {
        _playlist.value = playlistModel
    }

    fun findLocalPlaylist(id: Long) {

    }

    fun findRemotePlaylist(id: Long) {

    }

    fun findAllLocalTracksPlaylist() {
        _playlist.value?.run {
            setPlaylist(getAllTracksFromLocalStoreUseCase.execute())
        }
    }
}