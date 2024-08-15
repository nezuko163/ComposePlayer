package com.nezuko.composeplayer.app.ui.screens.profileScreen

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nezuko.composeplayer.app.ui.nav.RoutesNames
import com.nezuko.composeplayer.app.ui.views.PreviewTopAppBarColumn

const val USER_ID = "user_id"


fun NavController.navigateToProfileScreen(uid: String) {
    navigate("${RoutesNames.PROFILE_SCREEN}/$uid")
}

fun NavGraphBuilder.profile(
    onNaviagetToCropImageScreen: (Uri) -> Unit
) {
    composable(
        route = "${RoutesNames.PROFILE_SCREEN}/{$USER_ID}"
    ) {
        val arguments = requireNotNull(it.arguments)
        val id = arguments.getString(USER_ID) ?: return@composable

        Profile(uid = id, onNaviagetToCropImageScreen = onNaviagetToCropImageScreen)
    }
}