package com.nezuko.composeplayer.app.utils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat

class Permission2(val activity: ComponentActivity) {
    private val requestNotificationPermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(activity, "Notification permission granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(activity, "Notification permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private val requestReadAudioPermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(activity, "Read audio files permission granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(activity, "Read audio files permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        when {
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted
                Toast.makeText(
                    activity,
                    "Notification permission already granted",
                    Toast.LENGTH_SHORT
                ).show()
            }

            activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                // Show an explanation to the user asynchronously
                Toast.makeText(
                    activity,
                    "Notification permission is needed to show notifications",
                    Toast.LENGTH_SHORT
                ).show()
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

            }

            else -> {
                // Request the permission
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun requestReadAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.READ_MEDIA_AUDIO
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission already granted
                    Toast.makeText(
                        activity,
                        "Read audio files permission already granted",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_AUDIO) -> {
                    // Show an explanation to the user asynchronously
                    Toast.makeText(
                        activity,
                        "Read audio files permission is needed to access audio files",
                        Toast.LENGTH_SHORT
                    ).show()
                    requestReadAudioPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
                }

                else -> {
                    // Request the permission
                    requestReadAudioPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
                }
            }
        } else {
            when {
                ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission already granted
                    Toast.makeText(
                        activity,
                        "Read audio files permission already granted",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    // Show an explanation to the user asynchronously
                    Toast.makeText(
                        activity,
                        "Read audio files permission is needed to access audio files",
                        Toast.LENGTH_SHORT
                    ).show()
                    requestReadAudioPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }

                else -> {
                    // Request the permission
                    requestReadAudioPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    }
    fun execute(onGranted: () -> Unit) {
        requestNotificationPermission()
        requestReadAudioPermission()

        onGranted.invoke()
    }
}