package com.nezuko.composeplayer.app.ui.screens.libraryScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val playlists = viewModel.playlistsList.value
    val context = LocalContext.current

    if (playlists == null) {
        Text(text = "Плейлистов нетууу", modifier.fillMaxSize(), textAlign = TextAlign.Center)
    } else {
        PlaylistCard(
            Playlist = viewModel.allTracksPlaylist,
            onPlaylistClick = onPlaylistClick,
            onDotsClick = onDotsCLick
        )

        playlists.forEachIndexed { index: Int, playlist: Playlist ->
            PlaylistCard(
                Playlist = playlist,
                onPlaylistClick = onPlaylistClick,
                onDotsClick = onDotsCLick
            )
        }
    }
}
