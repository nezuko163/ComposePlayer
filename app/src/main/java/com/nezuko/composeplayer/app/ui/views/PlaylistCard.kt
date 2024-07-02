package com.nezuko.composeplayer.app.ui.views

import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.nezuko.composeplayer.R
import com.nezuko.composeplayer.app.utils.bitmapFromResId
import com.nezuko.composeplayer.app.utils.getBitmapFromUri
import com.nezuko.domain.model.PlaylistModel


@Composable
fun PlaylistCard(
    playlistModel: PlaylistModel,
    onPlaylistClick: (PlaylistModel) -> Unit,
    onDotsClick: (PlaylistModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    var bitmap = getBitmapFromUri(
        playlistModel.artUriString.toUri(),
        LocalContext.current
    )

    if (bitmap == null) {
        bitmap = bitmapFromResId(LocalContext.current, com.nezuko.data.R.drawable.img)
    }

    Surface(
        onClick = { onPlaylistClick.invoke(playlistModel) } ,
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .padding(5.dp)
                        .background(Color.Green)
                        .defaultMinSize(minHeight = 70.dp, minWidth = 70.dp)
                ) {
                    Image(
                        bitmap = bitmap!!.asImageBitmap(), contentDescription = "обложка",
                        modifier = Modifier.align(Alignment.Center)
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
                        text = playlistModel.title, modifier = Modifier
                            .weight(0.5f)
                            .wrapContentHeight(Alignment.CenterVertically)
                    )
                    Text(text = playlistModel.owner, modifier = Modifier.weight(0.5f))
                }

                Image(
                    modifier = Modifier
                        .padding(vertical = 30.dp)
                        .background(Color.White)
                        .clickable { onDotsClick.invoke(playlistModel) },
                    painter = painterResource(id = R.drawable.dots),
                    contentDescription = "больше",
                    contentScale = ContentScale.Fit,
                )
            }

        }
    }
}
