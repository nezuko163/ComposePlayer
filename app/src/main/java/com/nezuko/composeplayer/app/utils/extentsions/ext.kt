package com.nezuko.composeplayer.app.utils.extentsions

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nezuko.composeplayer.app.ui.libraryScreen.LibraryScreen
import com.nezuko.composeplayer.app.ui.mainScreen.MainScreen
import com.nezuko.composeplayer.app.ui.nav.RoutesNames
import com.nezuko.composeplayer.app.ui.searchScreen.SearchScreen

fun NavGraphBuilder.main(
) {
    composable(RoutesNames.MAIN_SCREEN) {
        MainScreen()
    }
}

fun NavGraphBuilder.library(
) {
    composable(RoutesNames.MY_LIBRARY_SCREEN) {
        LibraryScreen()
    }
}

fun NavGraphBuilder.search(
) {
    composable(RoutesNames.SEARCH_SCREEN) {
        SearchScreen()
    }
}


fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
