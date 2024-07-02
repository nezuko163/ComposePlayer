package com.nezuko.composeplayer.app.utils.extentsions

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nezuko.composeplayer.app.ui.screens.libraryScreen.LibraryScreen
import com.nezuko.composeplayer.app.ui.screens.mainScreen.MainScreen
import com.nezuko.composeplayer.app.ui.nav.RoutesNames
import com.nezuko.composeplayer.app.ui.screens.playlistScreen.PlaylistScreen
import com.nezuko.composeplayer.app.ui.screens.searchScreen.SearchScreen
import com.nezuko.domain.model.PlaylistModel


const val ID = "id"
const val IS_REMOTE = "isRemote"
fun NavGraphBuilder.main(
) {
    composable(
        RoutesNames.MAIN_SCREEN,
//        enterTransition = { fadeIn(animationSpec = tween(0)) },
//        exitTransition = { fadeOut(animationSpec = tween(0)) }
    ) {
        MainScreen()
    }
}

fun NavGraphBuilder.library(
    onPlaylistClick: (playlist: PlaylistModel) -> Unit,
    onDotsClick: (PlaylistModel) -> Unit
) {
    composable(
        RoutesNames.MY_LIBRARY_SCREEN,
//        enterTransition = { fadeIn(animationSpec = tween(0)) },
//        exitTransition = { fadeOut(animationSpec = tween(0)) }
    ) {
        LibraryScreen(Modifier, onPlaylistClick, onDotsClick)
    }
}

fun NavGraphBuilder.search(
) {
    composable(
        RoutesNames.SEARCH_SCREEN,
//        enterTransition = { fadeIn(animationSpec = tween(0)) },
//        exitTransition = { fadeOut(animationSpec = tween(0)) }
    ) {
        SearchScreen()
    }
}

fun NavGraphBuilder.playlist() {
    composable(
        "${RoutesNames.MY_LIBRARY_SCREEN}/{$ID}?$IS_REMOTE={$IS_REMOTE}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.LongType
                nullable = false
            },
            navArgument("isRemote") {
                type = NavType.BoolType
                nullable = false
            }
        )
//        enterTransition = { fadeIn(animationSpec = tween(0)) },
//        exitTransition = { fadeOut(animationSpec = tween(0)) }
    ) {
        val arguments = requireNotNull(it.arguments)
        val id = arguments.getLong("id")
        val isRemote = arguments.getBoolean("isRemote")

        PlaylistScreen(id, isRemote)
    }
}

fun NavController.navigateToPlaylistScreen(
    id: Long,
    isRemote: Boolean
) {
    navigate("${RoutesNames.MY_LIBRARY_SCREEN}/{$ID}?$IS_REMOTE={$IS_REMOTE}")
}

fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
