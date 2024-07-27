package com.nezuko.composeplayer.app.utils

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModelStoreOwner
import org.koin.androidx.compose.koinViewModel

@Composable
inline fun <reified T : ViewModel> getGlobalViewModel(viewModelClass: Class<T>, storeOwner: ViewModelStoreOwner): T {
    return koinViewModel(viewModelStoreOwner = storeOwner, key = "singleton")
}