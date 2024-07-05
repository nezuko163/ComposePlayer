package com.nezuko.composeplayer.app.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.nezuko.composeplayer.app.ui.nav.AppNavigation
import com.nezuko.composeplayer.app.ui.screens.loginScreen.LoginScreen
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.composeplayer.app.ui.views.MyBottomNavigation
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.compose.koinViewModel

const val TAG = "APP_MEH"

@Composable
fun App(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = koinViewModel()
) {

    val uid by userViewModel.uid.observeAsState()
    
    LaunchedEffect(Unit) {
        userViewModel.getCurrentUser()
    }
    BackHandler {
        navController.popBackStack()
    }
    Log.i(TAG, "App: $uid")
    if (uid.isNullOrEmpty()) {
        LoginScreen()
    } else {
        Log.i(TAG, "App: ")
        Scaffold(
            bottomBar = {
                MyBottomNavigation(navController)
            },
//                    containerColor = Color.Cyan
        ) {
            AppNavigation(
                navHostController = navController,
                modifier = Modifier.padding(it)
            )
        }
    }
}