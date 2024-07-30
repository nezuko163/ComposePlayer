package com.nezuko.composeplayer.app.ui.screens.libraryScreen.playlistScreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModelStoreOwner
import com.nezuko.composeplayer.app.ui.views.TracksList
import com.nezuko.composeplayer.app.utils.getGlobalViewModel
import com.nezuko.domain.model.Audio
import com.nezuko.domain.model.Playlist
import org.koin.androidx.compose.koinViewModel

private const val TAG = "PLAYLIST_SCREEN"

@Composable
fun PlaylistScreen(
    id: Long,
    modifier: Modifier = Modifier,
    playlistViewModel: PlaylistViewModel = koinViewModel(),
    playerServiceViewModel: PlayerServiceViewModel =
        getGlobalViewModel(
            viewModelClass = PlayerServiceViewModel::class.java,
            PlayerServiceViewModelStoreOwner
        )
) {

    Log.i(TAG, "PlaylistScreen: recomp")
    playlistViewModel.findPlaylist(id)
    val tracks by playlistViewModel.trackList.observeAsState()
    val trackId by playerServiceViewModel.currentQueueTrackId.observeAsState()
    Log.i(TAG, "PlaylistScreen: $trackId")

    TracksList(
        trackList =  tracks,
        onTrackClick =  { audio: Audio ->
            if (audio.meduaUrl.isNotEmpty()) {
                if (playerServiceViewModel.queue.value == tracks) {
                    Log.i(TAG, "PlaylistScreen: audio = ${playerServiceViewModel.audio}")
//                    Log.i(TAG, "PlaylistScreen: playerServiceViewModel.audio = ${playerServiceViewModel.audio.value}")
                    if (playerServiceViewModel.audio.value?.queueId == audio.queueId) {
                        playerServiceViewModel.playOrPause()
                        Log.i(TAG, "PlaylistScreen: pause")
                    } else {
                        playerServiceViewModel.updateQueueTrackId(audio.queueId)
                        playerServiceViewModel.play()
                        Log.i(TAG, "PlaylistScreen: play")
                    }
                } else {
                    playerServiceViewModel.setQueue(tracks!!)
                    playerServiceViewModel.updateQueueTrackId(audio.queueId)
                    playerServiceViewModel.play()
                }
            }
        },
        playingTrackIndex = trackId?.toInt() ?: -1
    )
}