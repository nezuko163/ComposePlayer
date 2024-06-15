package com.nezuko.composeplayer.app.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nezuko.composeplayer.R
import com.nezuko.composeplayer.app.ui.nav.RoutesNames

enum class BottomTabs(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    val route: String
) {
    HOME(
        R.string.home,
        R.drawable.left,
        RoutesNames.MAIN_SCREEN
    ),

    SEARCH(
        R.string.search,
        R.drawable.left,
        RoutesNames.SEARCH_SCREEN
    ),

    LIBRARY(
        R.string.library,
        R.drawable.left,
        RoutesNames.MY_LIBRARY_SCREEN
    ),
}