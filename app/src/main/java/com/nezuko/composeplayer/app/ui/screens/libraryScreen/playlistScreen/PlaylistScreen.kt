package com.nezuko.composeplayer.app.ui.screens.libraryScreen.playlistScreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.nezuko.composeplayer.app.ui.views.TracksList
import org.koin.androidx.compose.koinViewModel

const val TAG = "PLAYLIST_SCREEN"

@Composable
fun PlaylistScreen(
    id: Long,
    modifier: Modifier = Modifier,
    playlistViewModel: PlaylistViewModel = koinViewModel()
) {
    playlistViewModel.findPlaylist(id)
    val tracks by playlistViewModel.trackList.observeAsState()

    Log.i(TAG, "PlaylistScreen: 123")
    TracksList(tracks)
}