package com.nezuko.composeplayer.app.ui.views

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationDefaults
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nezuko.composeplayer.R
import com.nezuko.composeplayer.R.string.home
import com.nezuko.composeplayer.app.utils.BottomTabs
import com.nezuko.composeplayer.app.utils.extentsions.lifecycleIsResumed
import com.nezuko.composeplayer.ui.theme.ComposePlayerTheme
import org.koin.dsl.koinApplication

@Composable
fun MyBottomNavigation(navController: NavController) {
    BottomNavigation(
//        windowInsets = BottomNavigationDefaults.windowInsets,
        backgroundColor = Color.Cyan,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: BottomTabs.HOME.route

//        val routes = remember { BottomTabs.entries.map { it.route } }
        val tabs = BottomTabs.entries

//        if (currentRoute !in routes) return@BottomNavigation

        tabs.forEach { tab ->
            BottomNavigationItem(
                selected = currentRoute.split("/")[0] == tab.route,
                onClick = {
                    if (tab.route != currentRoute) {
                        navController.navigate(tab.route) {
                            anim {
                                enter = 0
                                exit = 0
                                popEnter = 0
                                popExit = 0
                            }
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = tab.icon),
                        contentDescription = stringResource(id = tab.title)
                    )
                },
                label = {
                    Text(stringResource(id = tab.title).uppercase(java.util.Locale.getDefault()))
                },
                alwaysShowLabel = false,
                unselectedContentColor = LocalContentColor.current,
                selectedContentColor = Color.White,
                modifier = Modifier.navigationBarsPadding()
            )
        }
    }
}