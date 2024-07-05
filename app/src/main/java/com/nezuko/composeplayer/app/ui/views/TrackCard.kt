package com.nezuko.composeplayer.app.ui.views

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.nezuko.composeplayer.R
import com.nezuko.composeplayer.app.utils.bitmapFromResId
import com.nezuko.composeplayer.app.utils.getBitmapFromUri
import com.nezuko.domain.model.Audio


val TAG = "TRACK_CARD"

@Composable
fun TrackCard(
    audio: Audio,
    onTrackClick: (Audio) -> Unit = {},
    onDotsClick: (Audio) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        onClick = { onTrackClick.invoke(audio) },
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
                    modifier = modifier.align(Alignment.Center),
                    error = painterResource(id = com.nezuko.data.R.drawable.img)
                )
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
                        .wrapContentHeight(Alignment.CenterVertically)
                )
                Text(
                    text = audio.artist,
                    modifier = Modifier
                        .weight(0.5f)
//                            .wrapContentHeight(Alignment.CenterVertically)
                )
            }

//            Image(
//                modifier = Modifier
//                    .padding(vertical = 25.dp)
//                    .background(Color.White)
//                    .clickable { onDotsClick.invoke(audio) },
//                painter = painterResource(id = R.drawable.dots),
//                contentDescription = "больше",
//                contentScale = ContentScale.Fit,
//            )

            AsyncImage(
                model = R.drawable.dots, contentDescription = "жопа",
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .background(Color.White)
                    .clickable { onDotsClick.invoke(audio) },
                contentScale = ContentScale.Fit
            )
        }
    }

//    Text(text = audio.title)
    Log.i(TAG, "TrackCard: ${audio.title}")
}