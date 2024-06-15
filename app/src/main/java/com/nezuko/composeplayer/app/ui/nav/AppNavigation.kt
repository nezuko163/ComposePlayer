package com.nezuko.composeplayer.app.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nezuko.composeplayer.app.utils.extentsions.library
import com.nezuko.composeplayer.app.utils.extentsions.main
import com.nezuko.composeplayer.app.utils.extentsions.search


@Composable
fun AppNavigation(navHostController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navHostController,
        startDestination = RoutesNames.MAIN_SCREEN,
        modifier = modifier
    ) {
        main()

        search()

        library()
    }
}