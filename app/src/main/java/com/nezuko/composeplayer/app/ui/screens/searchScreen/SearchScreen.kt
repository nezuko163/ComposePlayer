package com.nezuko.composeplayer.app.ui.screens.searchScreen


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModelStoreOwner
import com.nezuko.composeplayer.app.utils.getGlobalViewModel
import com.nezuko.composeplayer.ui.theme.LightBlue

private val TAG = "SEARCH_SCREEN"

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun SearchScreen(
    playerServiceViewModel: PlayerServiceViewModel =
        getGlobalViewModel(
            viewModelClass = PlayerServiceViewModel::class.java,
            PlayerServiceViewModelStoreOwner
        ),


    ) {
    Log.i(TAG, "SearchScreen: recomp")

    val queue by playerServiceViewModel.queue.observeAsState()

    if (queue == null) return

    val pagerState = rememberPagerState(pageCount = { queue!!.size })
    
    HorizontalPager(
        modifier = Modifier.background(LightBlue),
        state = pagerState
    ) { page: Int ->
        Text(text = queue!![page].title)
    }
}