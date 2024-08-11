package com.nezuko.composeplayer.app.ui.screens.profileScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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


}