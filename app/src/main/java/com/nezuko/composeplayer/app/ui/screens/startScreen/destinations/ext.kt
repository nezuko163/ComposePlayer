package com.nezuko.composeplayer.app.ui.screens.startScreen.destinations

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nezuko.composeplayer.app.ui.nav.RoutesNames
import com.nezuko.composeplayer.app.ui.screens.mainScreen.MainScreen
import com.nezuko.composeplayer.app.ui.screens.startScreen.StartScreen
import com.nezuko.composeplayer.app.ui.screens.startScreen.loginScreen.LoginScreen
import com.nezuko.composeplayer.app.ui.screens.startScreen.registerScreen.RegisterScreen
import com.nezuko.composeplayer.app.utils.ShowMainFeature

fun NavGraphBuilder.start(
    onSignInCLick: () -> Unit = {},
    onSignUpCLick: () -> Unit = {},
) {
    composable(
        RoutesNames.START_SCREEN,
//        enterTransition = { fadeIn(animationSpec = tween(0)) },
//        exitTransition = { fadeOut(animationSpec = tween(0)) }
    ) {
        StartScreen(onSignInCLick, onSignUpCLick)
    }
}


fun NavController.navigateToSignInScreen() {
    navigate(RoutesNames.SIGN_IN_SCREEN)
}

fun NavController.navigateToSignUpScreen() {
    navigate(RoutesNames.SIGN_UP_SCREEN)
}


fun NavGraphBuilder.signIn(onAuthComplete: (String) -> Unit) {
    composable(RoutesNames.SIGN_IN_SCREEN) {
        LoginScreen(onAuthComplete)
    }
}

fun NavGraphBuilder.signUp(onAuthComplete: (String) -> Unit) {
    composable(RoutesNames.SIGN_UP_SCREEN) {
        RegisterScreen(onAuthComplete)
    }
}