package com.nezuko.composeplayer.app.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat.checkSelfPermission

class Permission(private val activity: ComponentActivity) {
    private val TAG = "PERMISSION_ZALUPA"
    private val permissions = arrayOf(
        Manifest.permission.READ_MEDIA_AUDIO,
        Manifest.permission.POST_NOTIFICATIONS,
    )

    fun checkPermission(permission: String) =
        (checkSelfPermission(activity.applicationContext, permission)
                == PackageManager.PERMISSION_GRANTED)

    fun requestPermission(permissions: Array<String>) {
        activity.requestPermissions(permissions, 1)
    }

    fun execute(onGranted: () -> Unit) {
        if (permissions.all { checkPermission(it) }) {
            onGranted.invoke()
        } else {
            requestPermission(permissions)
        }
    }
}