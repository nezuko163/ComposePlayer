package com.nezuko.composeplayer.app.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModel
import com.nezuko.composeplayer.app.utils.Permission
import com.nezuko.composeplayer.app.utils.Permission2
import com.nezuko.composeplayer.app.utils.PermissionUtil
import com.nezuko.composeplayer.ui.theme.ComposePlayerTheme
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {
    private val TAG = "MAIN_ACTIVTY"
    private lateinit var playerServiceViewModel: PlayerServiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Log.i(TAG, "onCreate: ")
        Permission2(this).execute { init() }
    }

    private fun init() {
        Log.i(TAG, "init: 123")
        setContent {
            playerServiceViewModel = koinViewModel()
            playerServiceViewModel.onStart()
            ComposePlayerTheme {
                val navController = rememberNavController()
                App(navController)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (::playerServiceViewModel.isInitialized) playerServiceViewModel.onStart()
    }

    override fun onStop() {
        super.onStop()

        if (::playerServiceViewModel.isInitialized) playerServiceViewModel.onStop()
    }
}