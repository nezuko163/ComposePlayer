package com.nezuko.composeplayer.app.ui

import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModelStoreOwner
import com.nezuko.composeplayer.app.utils.Permission2
import com.nezuko.composeplayer.app.utils.getGlobalViewModel
import com.nezuko.composeplayer.ui.theme.ComposePlayerTheme


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
            playerServiceViewModel =
                getGlobalViewModel(
                    viewModelClass = PlayerServiceViewModel::class.java,
                    PlayerServiceViewModelStoreOwner
                )
            playerServiceViewModel.onStart()
            ComposePlayerTheme {
                val navController = rememberNavController()
                App(navController)
            }
        }


    }

    override fun onStart() {
        super.onStart()

        if (::playerServiceViewModel.isInitialized) {
            playerServiceViewModel.onStart()

        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (::playerServiceViewModel.isInitialized) playerServiceViewModel.onStop()
    }
}