package com.nezuko.composeplayer.app.ui.views

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModelStoreOwner
import com.nezuko.composeplayer.app.ui.viewmodels.ShouldShowBottomBarViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.ShouldShowBottpmBarVMStoreOwner
import com.nezuko.composeplayer.app.utils.getGlobalViewModel
import com.nezuko.composeplayer.ui.theme.LightBlue
import com.nezuko.data.R
import com.nezuko.domain.model.Audio
import com.nezuko.domain.model.PlaybackState
import kotlinx.coroutines.launch

private val TAG = "PLAYING_TRACK_BOTTOM_BAR"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayingTrackBottomBar(
    onClick: (Audio) -> Unit = {},
    playerServiceViewModel: PlayerServiceViewModel =
        getGlobalViewModel(
            viewModelClass = PlayerServiceViewModel::class.java,
            PlayerServiceViewModelStoreOwner
        ),
    shouldShowBottomBarVM: ShouldShowBottomBarViewModel =
        getGlobalViewModel(
            viewModelClass = ShouldShowBottomBarViewModel::class.java,
            storeOwner = ShouldShowBottpmBarVMStoreOwner
        )
) {
    val queue by playerServiceViewModel.queue.observeAsState()
    val isPlaying by playerServiceViewModel.isPlaying.observeAsState()
    val trackId by playerServiceViewModel.currentQueueTrackId.observeAsState()
    val shouldShow by shouldShowBottomBarVM.shouldShow.observeAsState()
    var isFirst = true

    if (isPlaying == null) return

    if (shouldShow != null) if (!shouldShow!!) return


    val pagerState =
        rememberPagerState(
            pageCount = { queue?.size ?: 0 },
            initialPage = (trackId ?: 0).toInt()
        )
    val coroutineScope = rememberCoroutineScope()


    Log.i(TAG, "SearchScreen: recomp")

    LaunchedEffect(trackId) {
        coroutineScope.launch {
            if (trackId == null) return@launch
            pagerState.animateScrollToPage(trackId!!.toInt())
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { page ->
            Log.i(TAG, "SearchScreen: pager updated")
            if (isFirst) {
                isFirst = false
            } else {
                playerServiceViewModel.updateQueueTrackId(page.toLong())
            }
        }
    }


    HorizontalPager(
        modifier = Modifier.background(LightBlue),
        state = pagerState
    ) { page: Int ->
        PlayingTrackBottomBarView(
            onClick = onClick,
            onPlayOrPauseClick =  { playerServiceViewModel.playOrPause() },
            audio = playerServiceViewModel.audio.value!!,
            isPlaying = isPlaying!!
        )
    }
}


@Preview
@Composable
private fun PlayingTrackBottomBarView(
    onClick: (Audio) -> Unit = {},
    onPlayOrPauseClick: () -> Unit = {},
    audio: Audio = Audio(),
    isPlaying: Boolean = false
) {
    Log.i(TAG, "PlayingTrackBottomBarView: recomp")


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .border(2.dp, color = Color.Black),
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
                    .padding(vertical = 22.dp)
                    .background(Color.White)
                    .clickable { onPlayOrPauseClick.invoke() },
                painter = painterResource(
                    id = if (isPlaying)
                        R.drawable.pause else R.drawable.play
                ),
                contentDescription = "больше",
                contentScale = ContentScale.Fit,
            )
        }
    }
}