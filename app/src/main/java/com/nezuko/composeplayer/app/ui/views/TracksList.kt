package com.nezuko.composeplayer.app.ui.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.nezuko.composeplayer.app.ui.screens.libraryScreen.playlistScreen.PlaylistViewModel
import com.nezuko.composeplayer.app.utils.repeatList
import com.nezuko.domain.model.Audio
import org.koin.androidx.compose.koinViewModel

const val TAG2 = "TRACK_LIST"

@Composable
fun TracksList(
    trackList: ArrayList<Audio>?,
    onTrackClick: (Audio) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current


//    if (trackList == null) return
    trackList?.run {
        val items = rememberOptimizedTrackList(trackList = trackList)
        Log.i(TAG2, "TracksList: recomp")
        LazyColumn {
            items(
                items = items,
                key = { audio: Audio -> audio.queueId }
            ) {
                TrackCard(
                    audio = it,
                    onTrackClick = { onTrackClick.invoke(it) },
                    onDotsClick = {}
                )
            }

        }

//        ПОТОМ МОЖНО ЧЕРЕЗ ПАГИНАЦИЮ КАК-ТО ЧТОБЫ НЕ ЛАГАЛО ПРИ СКРОЛЛЕ
//        Column(modifier.verticalScroll(rememberScrollState())) {
//            (trackList + trackList + trackList + trackList + trackList + trackList).forEach {
//                TrackCard(
//                    audio = it,
//                    onTrackClick = {
//                        Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
//                    },
//                    onDotsClick = {}
//                )
//            }
//        }
    }
}

@Composable
fun rememberOptimizedTrackList(trackList: List<Audio>): List<Audio> {
    return remember { trackList }
}