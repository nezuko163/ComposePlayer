package com.nezuko.composeplayer.app.ui.views

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nezuko.composeplayer.app.utils.BottomTabs

@Composable
fun MyBottomNavigation(navController: NavController) {
    NavigationBar(
//        windowInsets = BottomNavigationDefaults.windowInsets,
        containerColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: BottomTabs.HOME.route

//        val routes = remember { BottomTabs.entries.map { it.route } }
        val tabs = BottomTabs.entries

//        if (currentRoute !in routes) return@BottomNavigation

        tabs.forEach { tab ->
            NavigationBarItem(
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
                alwaysShowLabel = true,
                modifier = Modifier.navigationBarsPadding()
            )
        }
    }
}