package com.nezuko.composeplayer.app.ui.screens.libraryScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.nezuko.composeplayer.app.ui.views.PlaylistCard
import com.nezuko.domain.model.Playlist
import org.koin.androidx.compose.koinViewModel


val TAG = "LIBRARY_SCREEN"

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    onPlaylistClick: (Playlist) -> Unit,
    onDotsCLick: (Playlist) -> Unit
) {
    Column(modifier) {
        Text(text = "123")
        PlaylistsList(Modifier, onPlaylistClick, onDotsCLick)
    }
}

@Composable
fun PlaylistsList(
    modifier: Modifier = Modifier,
    onPlaylistClick: (Playlist) -> Unit,
    onDotsCLick: (Playlist) -> Unit,
    viewModel: PlaylistsViewModel = koinViewModel()
) {
    Log.i(TAG, "PlaylistsList: recomp")
    val playlists by viewModel.playlistsList.observeAsState()
    viewModel.loadPlaylists()

    if (playlists == null) {
        Text(text = "Плейлистов нетууу", modifier.fillMaxSize(), textAlign = TextAlign.Center)
    } else {
        Log.i(TAG, "PlaylistsList: $playlists")
        playlists!!.forEachIndexed { index: Int, playlist: Playlist ->
            PlaylistCard(
                playlist = playlist,
                onPlaylistClick = onPlaylistClick,
                onDotsClick = onDotsCLick
            )
        }
    }
}
