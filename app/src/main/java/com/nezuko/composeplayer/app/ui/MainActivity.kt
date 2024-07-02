package com.nezuko.composeplayer.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.nezuko.composeplayer.app.ui.nav.AppNavigation
import com.nezuko.composeplayer.app.ui.screens.loginScreen.LoginScreen
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.composeplayer.app.ui.views.MyBottomNavigation
import com.nezuko.composeplayer.ui.theme.ComposePlayerTheme


class MainActivity : ComponentActivity() {
    private val TAG = "MAIN_ACTIVTY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposePlayerTheme {
                val navController = rememberNavController()
                App(navController)
            }
        }
    }

}