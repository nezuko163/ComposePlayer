package com.nezuko.composeplayer.app.ui.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.nezuko.composeplayer.app.ui.screens.playlistScreen.PlaylistViewModel
import com.nezuko.domain.model.Audio
import org.koin.androidx.compose.koinViewModel

@Composable
fun TracksList(
    trackList: ArrayList<Audio>?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
//    if (trackList == null) return
    trackList?.run {
//        LazyColumn() {
//            items(
//                items = trackList + trackList + trackList + trackList +trackList + trackList,
//                itemContent = {
//                    TrackCard(
//                        audio = it,
//                        onTrackClick = {
//                            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
//                        },
//                        onDotsClick = {}
//                    )
//                }
//            )
//        }

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