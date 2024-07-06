package com.nezuko.composeplayer.app.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.nezuko.composeplayer.app.ui.nav.AppNavigation
import com.nezuko.composeplayer.app.ui.views.MyBottomNavigation

@Composable
fun ShowMainFeature(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            MyBottomNavigation(navController)
        },

        ) {
        AppNavigation(
            navHostController = navController,
            modifier = Modifier.padding(it)
        )
    }
}