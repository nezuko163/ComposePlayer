package com.nezuko.composeplayer.app.utils

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun galleryLauncher(
    onImageSelected: (Uri?) -> Unit
) =
    rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        onImageSelected(it)
    }


fun selectImage(launcher: ManagedActivityResultLauncher<String, Uri?>) {
    launcher.launch("image/*")
}