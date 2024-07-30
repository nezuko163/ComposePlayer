package com.nezuko.composeplayer.app.ui.nav

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nezuko.composeplayer.app.ui.screens.сontrolPlayingTrackScreen.controlPlaingTrack
import com.nezuko.composeplayer.app.utils.extentsions.library
import com.nezuko.composeplayer.app.utils.extentsions.main
import com.nezuko.composeplayer.app.utils.extentsions.navigateToPlaylistScreen
import com.nezuko.composeplayer.app.utils.extentsions.playlist
import com.nezuko.composeplayer.app.utils.extentsions.search
import com.nezuko.domain.model.Playlist


const val TAG = "APP_NAVIGATION"

@Composable
fun AppNavigation(navHostController: NavHostController, modifier: Modifier) {
    val context = LocalContext.current
    val onBackedPressedDispatcher = (context as ComponentActivity).onBackPressedDispatcher
    NavHost(
        navController = navHostController,
        startDestination = RoutesNames.MAIN_SCREEN,
        modifier = modifier,
    ) {
        main()

        search()

        library(
            onPlaylistClick = { playlist: Playlist ->
                navHostController.navigateToPlaylistScreen(playlist.id)
            },
            onDotsClick = { playlist ->
                Toast.makeText(context, playlist.title, Toast.LENGTH_SHORT).show()
            }
        )
        playlist()

        controlPlaingTrack(onBackPressed = {
            onBackedPressedDispatcher.onBackPressed()
        })
    }
}