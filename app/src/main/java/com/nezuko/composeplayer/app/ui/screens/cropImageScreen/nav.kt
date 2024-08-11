package com.nezuko.composeplayer.app.ui.screens.cropImageScreen

import android.net.Uri
import androidx.activity.OnBackPressedDispatcher
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nezuko.composeplayer.app.ui.nav.RoutesNames

private const val URI = "uri"

fun NavGraphBuilder.cropImage(
    onBackPressedDispatcher: () -> Unit
) {
    composable(
        route = "${RoutesNames.CROP_IMAGE_SCREEN}?$URI={$URI}",
        arguments = listOf(
            navArgument(URI) {
                type = NavType.StringType
                nullable = false
            }
        )
    ) {
        val arguments = requireNotNull(it.arguments)
        val encodedUri = arguments.getString(URI)
        val uri = Uri.decode(encodedUri)
        CropImage(imageUri = uri.toUri(), onBackPressed = onBackPressedDispatcher)
    }
}

fun NavController.navigateToCropImageScreen(uri: Uri) {
    val encodedUri = Uri.encode(uri.toString())
    navigate("${RoutesNames.CROP_IMAGE_SCREEN}?$URI=$encodedUri")
}