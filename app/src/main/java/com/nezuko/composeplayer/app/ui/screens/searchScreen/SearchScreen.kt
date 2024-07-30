package com.nezuko.composeplayer.app.ui.screens.searchScreen


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModelStoreOwner
import com.nezuko.composeplayer.app.utils.getGlobalViewModel
import com.nezuko.composeplayer.ui.theme.LightBlue
import kotlinx.coroutines.launch

private val TAG = "SEARCH_SCREEN"

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun SearchScreen(
    playerServiceViewModel: PlayerServiceViewModel =
        getGlobalViewModel(
            viewModelClass = PlayerServiceViewModel::class.java,
            storeOwner = PlayerServiceViewModelStoreOwner
        ),


    ) {
    val queue by playerServiceViewModel.queue.observeAsState()
    val trackId by playerServiceViewModel.currentQueueTrackId.observeAsState()

    var isFirst = true

    val pagerState =
        rememberPagerState(
            pageCount = { queue?.size ?: 0 },
            initialPage = (trackId ?: 0).toInt()
        )
    val coroutineScope = rememberCoroutineScope()
    

    Log.i(TAG, "SearchScreen: recomp")

    LaunchedEffect(trackId) {
        coroutineScope.launch {

            if (trackId == null) return@launch
            pagerState.animateScrollToPage(trackId!!.toInt())
        }
    }


    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { page ->
            Log.i(TAG, "SearchScreen: pager updated")
            if (isFirst) {
                isFirst = false
            } else {
                playerServiceViewModel.updateQueueTrackId(page.toLong())
            }
        }
    }


    HorizontalPager(
        modifier = Modifier.background(LightBlue),
        state = pagerState
    ) { page: Int ->
        Text(text = queue!![page].title)
    }
}