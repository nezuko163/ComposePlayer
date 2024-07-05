package com.nezuko.composeplayer.app.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.nezuko.composeplayer.app.utils.Permission
import com.nezuko.composeplayer.app.utils.PermissionUtil
import com.nezuko.composeplayer.ui.theme.ComposePlayerTheme


class MainActivity : ComponentActivity() {
    private val TAG = "MAIN_ACTIVTY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Permission(this).execute { init() }
    }

    private fun init() {
        setContent {
            ComposePlayerTheme {
                val navController = rememberNavController()
                App(navController)
            }
        }
    }

}