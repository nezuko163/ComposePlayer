package com.nezuko.composeplayer.app.ui.screens.сontrolPlayingTrackScreen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nezuko.composeplayer.app.ui.nav.RoutesNames

fun NavGraphBuilder.controlPlaingTrack(
    onBackPressed: () -> Unit = {},

) {
    composable(RoutesNames.CONTROL_PLAYING_TRACK_SCREEN) {
        ControlPlayingTrackScreen(onBackPressed = onBackPressed)
    }
}

fun NavController.navigateToControlPlaingTrack() {
    navigate(RoutesNames.CONTROL_PLAYING_TRACK_SCREEN)
}