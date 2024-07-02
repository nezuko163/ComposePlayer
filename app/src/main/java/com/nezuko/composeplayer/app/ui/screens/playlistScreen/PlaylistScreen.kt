package com.nezuko.composeplayer.app.ui.screens.playlistScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlaylistScreen(
    id: Long,
    isRemote: Boolean,
    modifier: Modifier = Modifier,
    playlistViewModel: PlaylistViewModel = koinViewModel()
) {

    if (isRemote) {
        println("todo")
    } else {
        if (id != -1L) {
            playlistViewModel.findLocalPlaylist(id)
        } else {
            playlistViewModel.findAllLocalTracksPlaylist()
        }
    }
}