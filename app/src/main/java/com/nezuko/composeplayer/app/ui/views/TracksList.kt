package com.nezuko.composeplayer.app.ui.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    LazyColumn(modifier = modifier) {
        trackList?.run {
            items(trackList) {
                TrackCard(
                    it,
                    { Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show() },
                    { Toast.makeText(context, "more", Toast.LENGTH_SHORT).show() })
            }
        }
    }
}