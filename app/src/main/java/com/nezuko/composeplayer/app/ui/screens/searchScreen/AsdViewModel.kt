package com.nezuko.composeplayer.app.ui.screens.searchScreen

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.nezuko.composeplayer.app.ui.viewmodels.ShouldShowBottomBarViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.ShouldShowBottpmBarVMStoreOwner
import com.nezuko.composeplayer.app.utils.getGlobalViewModel

class AsdViewModel : ViewModel() {
    private val _image = MutableLiveData<Uri>()
    val image: LiveData<Uri>
        get() = _image

    fun updateImage(uri: Uri) {
        _image.value = uri
    }
}

object AsdViewModelStoreOwner : ViewModelStoreOwner {
    private val _viewModelStore = ViewModelStore()

    override val viewModelStore: ViewModelStore
        get() = _viewModelStore
}

@Composable
fun getAsdViewModel() = getGlobalViewModel(
    viewModelClass = AsdViewModel::class.java,
    storeOwner = AsdViewModelStoreOwner
)