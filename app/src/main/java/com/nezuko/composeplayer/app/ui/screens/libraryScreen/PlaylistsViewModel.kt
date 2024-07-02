package com.nezuko.composeplayer.app.ui.screens.libraryScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.domain.model.PlaylistModel
import com.nezuko.domain.usecase.GetAllPlaylistsFromLocalUseCase
import com.nezuko.domain.usecase.GetAllTracksFromLocalStoreUseCase
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

class PlaylistsViewModel(
    private val getAllPlaylistsFromLocalUseCase: GetAllPlaylistsFromLocalUseCase,
    private val getAllTracksFromLocalStoreUseCase: GetAllTracksFromLocalStoreUseCase
) : ViewModel() {

    private val _playlistsList = MutableLiveData<ArrayList<PlaylistModel>>(arrayListOf())
    val playlistList: LiveData<ArrayList<PlaylistModel>>
        get() = _playlistsList

    var allTracksPlaylist: PlaylistModel? = null

    fun setPlaylist(list: ArrayList<PlaylistModel>) {
        _playlistsList.value = list
    }

    fun getLocalPlaylists() {
        viewModelScope.launch {
            _playlistsList.value?.run {
                getAllPlaylistsFromLocalUseCase.execute().forEach {
                    add(it)
                }
            }
        }
    }

    fun getAllLocalTracksPlaylist() {
        viewModelScope.launch {
            allTracksPlaylist = getAllTracksFromLocalStoreUseCase.execute()
        }
    }
}