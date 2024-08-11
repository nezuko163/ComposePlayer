package com.nezuko.composeplayer.app.ui.screens.searchScreen


import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.nezuko.composeplayer.R
import com.nezuko.composeplayer.app.utils.galleryLauncher

private val TAG = "SEARCH_SCREEN"


@Composable
fun SearchScreen(
    onNavigate: (uri: Uri) -> Unit,
    vm: AsdViewModel = getAsdViewModel()
) {
    val uri by vm.image.observeAsState()

    val launcher = galleryLauncher {
        if (it == null) return@galleryLauncher
        onNavigate(it)
    }

    IconButton(
        onClick = { launcher.launch("image/*") },
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = uri,
            contentDescription = "",
            error = painterResource(id = R.drawable.left),
        )
    }
}