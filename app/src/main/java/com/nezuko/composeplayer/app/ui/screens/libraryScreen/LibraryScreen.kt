package com.nezuko.composeplayer.app.ui.screens.libraryScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.nezuko.composeplayer.app.ui.views.PlaylistCard
import com.nezuko.domain.model.PlaylistModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    onPlaylistClick: (PlaylistModel) -> Unit,
    onDotsCLick: (PlaylistModel) -> Unit
) {
    Column(modifier) {
        Text(text = "123")
        PlaylistsList(Modifier, onPlaylistClick, onDotsCLick)
    }
}

@Composable
fun PlaylistsList(
    modifier: Modifier = Modifier,
    onPlaylistClick: (PlaylistModel) -> Unit,
    onDotsCLick: (PlaylistModel) -> Unit,
    viewModel: PlaylistsViewModel = koinViewModel()
) {
    viewModel.getAllLocalTracksPlaylist()
    viewModel.getLocalPlaylists()

    val playlists = viewModel.playlistList.value
    val allTracksPlaylist = viewModel.allTracksPlaylist
    val context = LocalContext.current

    if (playlists == null) {
        Text(text = "Плейлистов нетууу", modifier.fillMaxSize(), textAlign = TextAlign.Center)
    } else {
        if (allTracksPlaylist != null) {
            PlaylistCard(
                playlistModel = allTracksPlaylist,
                onPlaylistClick = onPlaylistClick,
                onDotsClick = onDotsCLick
            )
        }
        playlists.forEachIndexed { index: Int, playlist: PlaylistModel ->
            PlaylistCard(
                playlistModel = playlist,
                onPlaylistClick = onPlaylistClick,
                onDotsClick = onDotsCLick
            )
        }
    }

}
