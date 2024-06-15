package com.nezuko.composeplayer.app.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
import com.nezuko.composeplayer.app.ui.nav.AppNavigation
import com.nezuko.composeplayer.app.ui.nav.RoutesNames
import com.nezuko.composeplayer.app.ui.views.MyBottomNavigation
import com.nezuko.composeplayer.app.utils.BottomTabs
import com.nezuko.composeplayer.ui.theme.ComposePlayerTheme
import org.koin.androidx.scope.ScopeActivity


class MainActivity : ComponentActivity() {
    private val TAG = "MAIN_ACTIVTY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ComposePlayerTheme {
                val navController = rememberNavController()
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
    }
}