package com.nezuko.composeplayer.app.ui.views

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.nezuko.domain.model.Audio

private const val TAG = "TRACK_LIST"

@Composable
fun TracksList(
    trackList: ArrayList<Audio>?,
    onTrackClick: (Audio) -> Unit,
    playingTrackIndex: Int,
    modifier: Modifier = Modifier,
) {
    if (trackList == null) return

    val items = rememberOptimizedTrackList(trackList = trackList)
    Log.i(TAG, "TracksList: recomp")
    LazyColumn {
//            items(
//                items = items,
//                key = { audio: Audio -> audio.queueId }
//            ) {
//                TrackCard(
//                    audio = it,
//                    onTrackClick = { onTrackClick.invoke(it) },
//                    onDotsClick = {}
//                )
//            }

        itemsIndexed(
            items = items,
            key = { _: Int, audio: Audio -> audio.queueId })
        { index: Int, audio: Audio ->
            val isPlaying = index == playingTrackIndex
//            val isPlaying = index == playingTrackIndex
            TrackCard(
                audio = audio,
                isPlaying = isPlaying,
                onTrackClick = onTrackClick,
                onDotsClick = {}
            )
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