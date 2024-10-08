package com.nezuko.composeplayer.app.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.nezuko.composeplayer.app.ui.nav.AppNavigation
import com.nezuko.composeplayer.app.ui.nav.AuthNavigation
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.composeplayer.app.ui.screens.startScreen.StartScreen
import com.nezuko.composeplayer.app.ui.viewmodels.getUserViewModel
import com.nezuko.composeplayer.app.ui.views.MyBottomNavigation
import com.nezuko.composeplayer.app.utils.ShowMainFeature
import com.nezuko.domain.model.UserProfile
import org.koin.androidx.compose.koinViewModel

const val TAG = "APP_MEH"

@Composable
fun App(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = getUserViewModel()
) {
    val uid by userViewModel.uid.observeAsState()
    LaunchedEffect(Unit) {
        userViewModel.initializeApp()
        userViewModel.getCurrentUser()
    }

    BackHandler {
        navController.popBackStack()
    }

    Log.i(TAG, "App: id = $uid")
    if (!uid.isNullOrEmpty()) {
        userViewModel.getUserProfileById(
            uid!!,
            onSuccess = { user: UserProfile ->
                Log.i(TAG, "App: user = $user")
                userViewModel.updateUserProfile(user)
            },
            onFailure = {
                Log.i(TAG, "App: чо бля")
            }
        )
        ShowMainFeature(navController = navController)
    } else {
        AuthNavigation(
            navHostController = navController,
            onAuthComplete = { it: String ->
                userViewModel.setUID(it)
            },
        )


    }

//    Log.i(TAG, "App: $uid")
//    if (uid.isNullOrEmpty()) {
//        LoginScreen()
//    } else {
//        Log.i(TAG, "App: ")
//        Scaffold(
//            bottomBar = {
//                MyBottomNavigation(navController)
//            },
////                    containerColor = Color.Cyan
//        ) {
//            AppNavigation(
//                navHostController = navController,
//                modifier = Modifier.padding(it)
//            )
//        }
//    }
}