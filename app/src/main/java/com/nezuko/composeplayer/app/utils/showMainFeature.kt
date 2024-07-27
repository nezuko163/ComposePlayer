package com.nezuko.composeplayer.app.utils

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.nezuko.composeplayer.app.ui.nav.AppNavigation
import com.nezuko.composeplayer.app.ui.screens.сontrolPlayingTrackScreen.navigateToControlPlaingTrack
import com.nezuko.composeplayer.app.ui.views.MyBottomNavigation
import com.nezuko.composeplayer.app.ui.views.PlayingTrackBottomBar

@Composable
fun ShowMainFeature(navController: NavHostController) {
    val context = LocalContext.current
//    Scaffold(
//        bottomBar = { MyBottomNavigation(navController = navController) }
//    ) { it1 ->
//        Scaffold(
//            Modifier.padding(it1),
//            bottomBar = {
//                PlayingTrackBottomBar({
//                    Toast.makeText(context, "123", Toast.LENGTH_SHORT).show()
//                })
//            }
//        ) {
//            AppNavigation(
//                navHostController = navController,
//                modifier = Modifier.padding(it)
//            )
//        }
//    }

    Scaffold(
        bottomBar = {
            Column {
                PlayingTrackBottomBar(onClick = { navController.navigateToControlPlaingTrack() })
                MyBottomNavigation(navController)
            }
        },

        ) {
        AppNavigation(
            navHostController = navController,
            modifier = Modifier.padding(it)
        )
    }
}