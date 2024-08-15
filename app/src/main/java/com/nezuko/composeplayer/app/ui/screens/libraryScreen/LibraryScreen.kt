package com.nezuko.composeplayer.app.ui.screens.libraryScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nezuko.composeplayer.R
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.getUserViewModel
import com.nezuko.composeplayer.app.ui.views.PlaylistCard
import com.nezuko.composeplayer.app.utils.ImageFromFirebaseStorage
import com.nezuko.domain.model.Playlist
import org.koin.androidx.compose.koinViewModel


val TAG = "LIBRARY_SCREEN"

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    onPlaylistClick: (Playlist) -> Unit,
    onDotsCLick: (Playlist) -> Unit,
    onIconProfileClick: (uid: String) -> Unit,
    userViewModel: UserViewModel = getUserViewModel()
) {
    val context = LocalContext.current
    val user by userViewModel.userProfile.observeAsState()

    Log.i(TAG, "LibraryScreen: ${user?.artUrl}")
    
    Scaffold(
        topBar = {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "Медиатека",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                        .padding(start = 10.dp),
                )

                IconButton(
                    onClick = { onIconProfileClick(userViewModel.uid.value!!) },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp)
                        .clip(CircleShape)
                ) {
                    ImageFromFirebaseStorage(
                        imageUrl = user?.artUrl ?: "",
                        errorImageResource = R.drawable.left,
                        contentDescription = "ава")
                }

            }
        }
    ) {
        Column(modifier.padding(it)) {
            Text(text = "123")
            PlaylistsList(Modifier, onPlaylistClick, onDotsCLick)
        }
    }
}

@Composable
fun PlaylistsList(
    modifier: Modifier = Modifier,
    onPlaylistClick: (Playlist) -> Unit,
    onDotsCLick: (Playlist) -> Unit,
    viewModel: PlaylistsViewModel = koinViewModel()
) {
    Log.i(TAG, "PlaylistsList: recomp")
    val playlists by viewModel.playlistsList.observeAsState()
    viewModel.loadPlaylists()

    if (playlists == null) {
        Text(text = "Плейлистов нетууу", modifier.fillMaxSize(), textAlign = TextAlign.Center)
    } else {
        Log.i(TAG, "PlaylistsList: $playlists")
        playlists!!.forEachIndexed { index: Int, playlist: Playlist ->
            PlaylistCard(
                playlist = playlist,
                onPlaylistClick = onPlaylistClick,
                onDotsClick = onDotsCLick
            )
        }
    }
}
