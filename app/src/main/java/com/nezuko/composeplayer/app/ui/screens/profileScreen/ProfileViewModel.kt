package com.nezuko.composeplayer.app.ui.screens.profileScreen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.nezuko.composeplayer.app.ui.screens.searchScreen.AsdViewModel
import com.nezuko.composeplayer.app.utils.getGlobalViewModel
import com.nezuko.domain.model.UserProfile
import com.nezuko.domain.usecase.GetImageFromStorageByUrlUseCase
import com.nezuko.domain.usecase.SetAvatarToUserUseCase
import kotlinx.coroutines.CoroutineDispatcher

class ProfileViewModel(
    private val setAvatarToUserUseCase: SetAvatarToUserUseCase,
    private val getImageFromStorageByUrlUseCase: GetImageFromStorageByUrlUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val TAG = "ProfileViewModel"
    private val _user = MutableLiveData<UserProfile>()
    val user: LiveData<UserProfile>
        get() = _user

    fun updateUser(newUser: UserProfile) {
        _user.value = newUser
    }

    private val _uri = MutableLiveData<Uri?>()
    val uri: LiveData<Uri?>
        get() = _uri


    fun updateImage(uri: Uri?) {
        Log.i(TAG, "updateImage: $uri")
        _uri.value = uri
    }
}

object ProfileViewModelStoreOwner : ViewModelStoreOwner {
    private val _viewModelStore = ViewModelStore()

    override val viewModelStore: ViewModelStore
        get() = _viewModelStore
}

@Composable
fun getProfileViewModel() = getGlobalViewModel(
    viewModelClass = ProfileViewModel::class.java,
    storeOwner = ProfileViewModelStoreOwner
)