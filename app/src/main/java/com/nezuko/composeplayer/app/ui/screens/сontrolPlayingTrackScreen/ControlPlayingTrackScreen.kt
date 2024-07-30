package com.nezuko.composeplayer.app.ui.screens.сontrolPlayingTrackScreen

import android.hardware.lights.Light
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.KeyEventDispatcher.Component
import coil.compose.AsyncImage
import com.nezuko.composeplayer.R
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModelStoreOwner
import com.nezuko.composeplayer.app.utils.getGlobalViewModel
import com.nezuko.composeplayer.ui.theme.Aqua
import com.nezuko.composeplayer.ui.theme.LightBlue
import com.nezuko.domain.model.Audio
import com.nezuko.domain.model.PlaybackState


private val TAG = "ControlPlayingTrackScreen"

@Composable
fun ControlPlayingTrackScreen(
    onBackPressed: () -> Unit = {},
    playerServiceViewModel: PlayerServiceViewModel =
        getGlobalViewModel(
            viewModelClass = PlayerServiceViewModel::class.java,
            PlayerServiceViewModelStoreOwner
        )
) {
    ControlPlayingTrackView(
        audio = audio!!,
        queue = queue!!,
        onBackPressed = onBackPressed,
        onPopUpMenuClick = {},
        onPageChanged = { page ->
            playerServiceViewModel.updateQueueTrackId(page.toLong())
        }
    )
}

@Preview
@Composable
private fun ControlPlayingTrackView(
    audio: Audio = Audio(),
    queue: ArrayList<Audio> = arrayListOf(),
    onBackPressed: () -> Unit = {},
    onPopUpMenuClick: () -> Unit = {},
    onPageChanged: (Int) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.Top
        ) {

            Image(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterVertically)
                    .size(40.dp)
                    .background(Color.White)
                    .clickable { onBackPressed() },
                painter = painterResource(id = R.drawable.expand_more),
                contentDescription = "больше",
                contentScale = ContentScale.Fit,
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "ИЗ ПЛЕЙЛИСТА",
                    modifier = Modifier
                        .weight(0.5f)
                        .align(Alignment.CenterHorizontally)
                        .wrapContentHeight(Alignment.CenterVertically),
                    color = Color.Black
                )
                Text(
                    text = "жопа",
                    modifier = Modifier
                        .weight(0.5f)
                        .align(Alignment.CenterHorizontally),
                    color = Color.Black
                )
            }

            Image(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterVertically)
                    .size(40.dp)
                    .background(Color.White)
                    .clickable { onPopUpMenuClick.invoke() },
                painter = painterResource(id = R.drawable.dots),
                contentDescription = "больше",
                contentScale = ContentScale.Fit,
            )
        }

        PagerBlock(
            modifier = Modifier
                .padding()
                .size(350.dp)
                .padding(30.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(20.dp))
                .border(2.dp, color = Color.Black),
            queue = queue,
            onPageChanged = onPageChanged
        )

        StateBlock()

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = audio.title, modifier = Modifier.align(Alignment.CenterHorizontally))
        Text(
            text = audio.artist,
            color = LightBlue,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(10.dp))

        ButtonsBlock(Modifier.align(Alignment.CenterHorizontally))
    }
}


@Composable
fun StateBlock(
    playerServiceViewModel: PlayerServiceViewModel =
        getGlobalViewModel(
            viewModelClass = PlayerServiceViewModel::class.java,
            PlayerServiceViewModelStoreOwner
        )
) {

    val state by playerServiceViewModel.state.observeAsState()

    if (state == null) return

    StateBlockView(
        modifier = Modifier.padding(horizontal = 30.dp),
        state = state!!,
        onValueChanged = {},
        onValueFinishedChanged = { value ->
            playerServiceViewModel.state.value!!.position = value.toLong()
            playerServiceViewModel.seekTo(value.toLong())
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun StateBlockView(
    modifier: Modifier = Modifier,
    state: PlaybackState = PlaybackState(1L, 1L),
    onValueFinishedChanged: (Float) -> Unit = {},
    onValueChanged: (MutableFloatState) -> Unit = {},
) {
    var sliderPos by remember { mutableFloatStateOf(1f) }
    var isSliding by remember { mutableStateOf(false) }

    LaunchedEffect(state.position) {
        sliderPos = state.position.toFloat()
    }

    Log.i(TAG, "StateBlockView: $state")
    Column(modifier = modifier) {
        Slider(
            value = sliderPos,
            onValueChange = { value ->
                sliderPos = value
                if (!isSliding) isSliding = true
            },
            onValueChangeFinished = {
                onValueFinishedChanged(sliderPos)
                isSliding = false
            },
            valueRange = 1f..state.duration.toFloat(),

            thumb = { it ->
                Canvas(modifier = Modifier.size(20.dp)) {
                    drawCircle(
                        color = Aqua,
                        radius = 20f
                    )
                }
            },
            colors = SliderDefaults.colors(
                activeTrackColor = Aqua,
                inactiveTickColor = LightBlue,
                disabledInactiveTickColor = Color.Black
            )
        )

        Row(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = setTime(sliderPos.toLong()),
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentWidth(Alignment.Start)
            )

            Text(
                text = setTime(state.duration),
                modifier = Modifier
                    .weight(0.5f)
                    .wrapContentWidth(Alignment.End)
            )
        }
    }
}


@Composable
private fun ButtonsBlock(
    modifier: Modifier = Modifier,
    playerServiceViewModel: PlayerServiceViewModel =
        getGlobalViewModel(
            viewModelClass = PlayerServiceViewModel::class.java,
            storeOwner = PlayerServiceViewModelStoreOwner
        )
) {
    val isPlaying by playerServiceViewModel.isPlaying.observeAsState()

    if (isPlaying == null) return

    ButtonsBlockView(
        isPlaying = isPlaying!!,
        modifier = modifier,
        onPreviousClick = {
            playerServiceViewModel.previousTrack()
        },
        onPlayOrPauseClick = {
            playerServiceViewModel.playOrPause()
        },
        onNextClick = {
            playerServiceViewModel.nextTrack()
        }
    )
}

@Preview
@Composable
private fun ButtonsBlockView(
    isPlaying: Boolean = false,
    modifier: Modifier = Modifier,
    onPreviousClick: () -> Unit = {},
    onPlayOrPauseClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
) {
    Log.i(TAG, "ButtonsBlockView: recomp")
    val size = 80.dp
    Row(modifier = modifier) {
        Image(
            modifier = Modifier
                .size(size)
                .clickable { onPreviousClick() },
            contentScale = ContentScale.Fit,
            painter = painterResource(id = com.nezuko.data.R.drawable.skip_to_previous),
            contentDescription = "previous"
        )

        Spacer(modifier = Modifier.width(50.dp))

        Image(
            modifier = Modifier
                .size(size)
                .clickable { onPlayOrPauseClick() },
            contentScale = ContentScale.Fit,
            painter = painterResource(
                id =
                if (isPlaying) com.nezuko.data.R.drawable.pause
                else com.nezuko.data.R.drawable.play
            ),
            contentDescription = "pause or play"
        )

        Spacer(modifier = Modifier.width(50.dp))

        Image(
            modifier = Modifier
                .size(size)
                .clickable { onNextClick() },
            contentScale = ContentScale.Fit,
            painter = painterResource(id = com.nezuko.data.R.drawable.skip_to_next),
            contentDescription = "next"
        )
    }
}

@Composable
private fun PagerBlock(
    modifier: Modifier = Modifier,
    playerServiceViewModel: PlayerServiceViewModel =
        getGlobalViewModel(
            viewModelClass = PlayerServiceViewModel::class.java,
            storeOwner = PlayerServiceViewModelStoreOwner
        )
) {
    AsyncImage(
        model = audio.artUrl,
        error = painterResource(id = com.nezuko.data.R.drawable.img),
        contentDescription = "art",
        modifier = Modifier.focusModifier()
    )
}


private fun setTime(ms: Long): String {
    val sec = ms / 1000
    val min = sec / 60
    val secq = sec % 60
    var secs = "$secq"
    secs = "0".repeat(2 - secs.length) + secs
    return "$min:$secs"
}
