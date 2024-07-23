package com.nezuko.composeplayer.app.ui.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nezuko.composeplayer.app.ui.screens.libraryScreen.playlistScreen.PlaylistViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModel
import com.nezuko.data.R
import com.nezuko.data.service.Player
import com.nezuko.domain.model.Audio
import com.nezuko.domain.model.PlaybackState
import org.koin.androidx.compose.koinViewModel

const val TAG3 = "PLAYING_TRACK_BOTTOM_BAR"

@Preview
@Composable
fun PlayingTrackBottomBar(
    onClick: (Audio) -> Unit = {},
    playerServiceViewModel: PlayerServiceViewModel = koinViewModel()
) {
    val audio = playerServiceViewModel.audio
    val state = playerServiceViewModel.state

    Log.i(TAG3, "PlayingTrackBottomBar: $audio")
    Log.i(TAG3, "PlayingTrackBottomBar: $state")

    if (audio != null) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            onClick = { audio.let(onClick) },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .padding(5.dp)
                        .height(55.dp)
                        .width(55.dp)

                ) {
                    AsyncImage(
                        model = audio.artUrl,
                        contentDescription = "123",
                        modifier = Modifier.align(Alignment.Center),
                        error = painterResource(id = R.drawable.img)
                    )
//                Image(
//                    painter = rememberAsyncImagePainter(
//                        model = audio.artUrl,
//                        error = painterResource(id = com.nezuko.data.R.drawable.img).also {
//                            Log.i(TAG, "TrackCard: $audio")
//                        }
//                    ),
//                    contentDescription = "123",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.align(Alignment.Center),
//                )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(horizontal = 5.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = audio.title, modifier = Modifier
                            .weight(0.5f)
                            .wrapContentHeight(Alignment.CenterVertically),
                        color = Color.Black
                    )
                    Text(
                        text = audio.artist,
                        modifier = Modifier
                            .weight(0.5f),
                        color = Color.Black
                    )
                }

                Image(
                    modifier = Modifier
                        .padding(vertical = 22.dp, horizontal = 10.dp)
                        .background(Color.White)
                        .clickable { playerServiceViewModel.playOrPause() },
                    painter = painterResource(
                        id = if (state?.state == PlaybackState.State.PLAYING)
                            R.drawable.pause else R.drawable.play
                    ),
                    contentDescription = "больше",
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }
}