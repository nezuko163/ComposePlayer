package com.nezuko.composeplayer.app.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun BottomBar(modifier: Modifier = Modifier, navHostController: NavHostController) {
    Column(modifier) {

        MyBottomNavigation(navController = navHostController)
    }
}