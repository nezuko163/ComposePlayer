package com.nezuko.composeplayer.app.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nezuko.composeplayer.app.ui.screens.playlistScreen.PlaylistViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlaylistList(
    playlistViewModel: PlaylistViewModel = koinViewModel(),
    modifier: Modifier = Modifier,

) {
    Column(modifier = modifier) {
        playlistViewModel.playlist.value?.run {
            tracksList.forEach {
                TrackCard(
                    audio = it,
                    onTrackClick = {

                    }
                )
            }
        }
    }
}