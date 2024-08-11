package com.nezuko.composeplayer.app.ui.viewmodels

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.nezuko.composeplayer.app.utils.getGlobalViewModel
import com.nezuko.domain.model.UserProfile
import com.nezuko.domain.usecase.GetCurrentUserIDUseCase
import com.nezuko.domain.usecase.GetUserProfileByUIDUseCase
import com.nezuko.domain.usecase.InitializeAppUseCase
import com.nezuko.domain.usecase.SetAvatarToUserUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class UserViewModel(
    private val getCurrentUserIDUseCase: GetCurrentUserIDUseCase,
    private val initializeAppUseCase: InitializeAppUseCase,
    private val getUserProfileByUIDUseCase: GetUserProfileByUIDUseCase,
    private val setAvatarToUserUseCase: SetAvatarToUserUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val TAG = "USER_VIEW_MODEL"

    // сделана хуйня через auth
    // теперь через source/remote чтобы подгружались плейлисты и юзернейм и фотка
    private val _uid = MutableLiveData("")

    val uid: LiveData<String>
        get() = _uid


    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile>
        get() = _userProfile

    fun updateUserProfile(newUserProfile: UserProfile) {
        _userProfile.postValue(newUserProfile)
    }

    fun getCurrentUser() {
        viewModelScope.launch(ioDispatcher) {
            Log.i(TAG, "getCurrentUser: ")
            getCurrentUserIDUseCase.execute().getOrNull().let {
                if (it != null) setUID(it)
            }
        }
    }

    fun setUID(id: String) {
        Log.i(TAG, "setUID: ${uid.hasObservers() || _uid.hasObservers()}")
        _uid.postValue(id)
    }

    fun getUserProfileById(
        uid: String,
        onSuccess: (UserProfile) -> Unit,
        onFailure: () -> Unit
    ) {
       viewModelScope.launch(ioDispatcher) {
           getUserProfileByUIDUseCase(uid, onSuccess, onFailure)
       }
    }

    fun setAvatarToUser(uri: Uri, onSuccess: (String) -> Unit) {
        viewModelScope.launch(ioDispatcher) {
            if (_userProfile.value == null) return@launch
            setAvatarToUserUseCase(
                user = _userProfile.value!!,
                uri = uri.toString(),
                onSuccess = { str ->
                    _userProfile.value!!.artUrl = str
                    onSuccess(str)
                    Log.i(TAG, "setAvatarToUser: $str")
                },
                onFailure = {
                    Log.e(TAG, "setAvatarToUser: failure unluck")
                }

            )
        }
    }

    fun initializeApp() = initializeAppUseCase.execute()
}

object UserViewModelStoreOwner : ViewModelStoreOwner {
    private val _viewModelStore = ViewModelStore()

    override val viewModelStore: ViewModelStore
        get() = _viewModelStore
}

@Composable
fun getUserViewModel() = getGlobalViewModel(
    viewModelClass = UserViewModel::class.java,
    storeOwner = UserViewModelStoreOwner
)