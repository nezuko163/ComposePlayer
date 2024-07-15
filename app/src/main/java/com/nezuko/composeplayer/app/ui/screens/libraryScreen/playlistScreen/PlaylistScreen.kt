package com.nezuko.composeplayer.app.ui.screens.libraryScreen.playlistScreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModel
import com.nezuko.composeplayer.app.ui.views.TracksList
import com.nezuko.domain.model.Audio
import org.koin.androidx.compose.koinViewModel

const val TAG = "PLAYLIST_SCREEN"

@Composable
fun PlaylistScreen(
    id: Long,
    modifier: Modifier = Modifier,
    playlistViewModel: PlaylistViewModel = koinViewModel(),
    playerServiceViewModel: PlayerServiceViewModel = koinViewModel()
) {
    playlistViewModel.findPlaylist(id)
    val tracks by playlistViewModel.trackList.observeAsState()

    Log.i(TAG, "PlaylistScreen: 123")
    TracksList(
        tracks,
        { audio: Audio ->
            if (audio.meduaUrl.isNotEmpty()) {
                Log.i(TAG, "PlaylistScreen: queue = ${playerServiceViewModel.queue.value}")
                Log.i(TAG, "PlaylistScreen: trackList = ${playlistViewModel.trackList.value}")
                if (playerServiceViewModel.queue.value == playlistViewModel.trackList.value) {
                    if (playerServiceViewModel.audio.value?.queueId != audio.queueId) {
                        playerServiceViewModel.pause()
                    } else {
                        playerServiceViewModel.skipToQueueItem(audio.queueId)
                        playerServiceViewModel.play()
                    }
                } else {
                    playerServiceViewModel.stop()
                    playerServiceViewModel.setQueue(playlistViewModel.trackList.value!!)
                    playerServiceViewModel.skipToQueueItem(audio.queueId)
                    playerServiceViewModel.play()
                }
            }
        }
    )
}