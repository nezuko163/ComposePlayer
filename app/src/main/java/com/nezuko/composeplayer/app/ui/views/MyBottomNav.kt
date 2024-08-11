package com.nezuko.composeplayer.app.ui.views

import android.util.Log
import androidx.compose.foundation.layout.Column
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
import com.nezuko.composeplayer.app.ui.viewmodels.ShouldShowBottomBarViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.ShouldShowBottpmBarVMStoreOwner
import com.nezuko.composeplayer.app.ui.viewmodels.getShouldShowBottomBarViewModel
import com.nezuko.composeplayer.app.utils.BottomTabs
import com.nezuko.composeplayer.app.utils.getGlobalViewModel


private val TAG = "BOTTOM_NAVIGATION"

@Composable
fun MyBottomNavigation(
    navController: NavController,
    shouldShowBottomBarVM: ShouldShowBottomBarViewModel = getShouldShowBottomBarViewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: BottomTabs.HOME.route
    val routes = BottomTabs.entries.map { it.route.split("/")[0] }
    val tabs = BottomTabs.entries

    Log.i(TAG, "MyBottomNavigation start: ${shouldShowBottomBarVM.shouldShow.value}")


    if (currentRoute.split("/")[0] !in routes) {
        shouldShowBottomBarVM.update(false)
        return
    } else {
        if (shouldShowBottomBarVM.shouldShow.value != true) shouldShowBottomBarVM.update(true)
    }

    Log.i(TAG, "MyBottomNavigation end: ${shouldShowBottomBarVM.shouldShow.value}")

    Column {
        NavigationBar(
//        windowInsets = BottomNavigationDefaults.windowInsets,
            containerColor = Color.White
        ) {


            Log.i(TAG, "MyBottomNavigation: $currentRoute")


            if (currentRoute.split("/")[0] !in routes) return@NavigationBar

            tabs.forEach { tab ->
                NavigationBarItem(
                    selected = currentRoute.split("/")[0] == tab.route,
                    onClick = {
                        if (tab.route != currentRoute) {
                            navController.navigate(tab.route) {
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
}