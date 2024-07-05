package com.nezuko.composeplayer.app.ui.screens.libraryScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.domain.model.Playlist
import com.nezuko.domain.usecase.GetAllLocalTracksUseCase
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val getAllLocalTracksUseCase: GetAllLocalTracksUseCase
) : ViewModel() {

    private val _playlistsList = MutableLiveData<ArrayList<Playlist>>(arrayListOf())
    val playlistsList: LiveData<ArrayList<Playlist>>
        get() = _playlistsList

    var allTracksPlaylist = getAllLocalTracksUseCase.execute()

    fun setPlaylist(list: ArrayList<Playlist>) {
        _playlistsList.value = list
    }
}