package com.nezuko.composeplayer.app.ui.nav

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nezuko.composeplayer.app.ui.screens.startScreen.destinations.navigateToRegisterProfileScreen
import com.nezuko.composeplayer.app.ui.screens.startScreen.destinations.navigateToSignInScreen
import com.nezuko.composeplayer.app.ui.screens.startScreen.destinations.navigateToSignUpScreen
import com.nezuko.composeplayer.app.ui.screens.startScreen.destinations.registerProfile
import com.nezuko.composeplayer.app.ui.screens.startScreen.destinations.signIn
import com.nezuko.composeplayer.app.ui.screens.startScreen.destinations.signUp
import com.nezuko.composeplayer.app.ui.screens.startScreen.destinations.start

@Composable
fun AuthNavigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    onAuthComplete: (String) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = RoutesNames.START_SCREEN,
        modifier = modifier,

        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popExitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None }
    ) {
        start(
            onSignInCLick = {
                navHostController.navigateToSignInScreen()
            },
            onSignUpCLick = {
                navHostController.navigateToSignUpScreen()
            }
        )

        signIn(onAuthComplete)

        signUp(
            onNavigateToRegisterProfileScreen = {
                navHostController.navigateToRegisterProfileScreen()
            }
        )

        registerProfile(onAuthComplete)
    }
}