package com.nezuko.composeplayer.app.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.domain.usecase.GetCurrentUserIDUseCase
import com.nezuko.domain.usecase.InitializeAppUseCase
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.compose.koinInject

class UserViewModel(
    private val getCurrentUserIDUseCase: GetCurrentUserIDUseCase,
    private val initializeAppUseCase: InitializeAppUseCase
) : ViewModel() {
    private val TAG = "USER_VIEW_MODEL"

    // сделана хуйня через auth
    // теперь через source/remote чтобы подгружались плейлисты и юзернейм и фотка
    private val _uid = MutableLiveData("")

    val uid: LiveData<String>
        get() = _uid

    fun getCurrentUser() {
        viewModelScope.launch {
            Log.i(TAG, "getCurrentUser: ")
            getCurrentUserIDUseCase.execute().getOrNull().let {
                if (it != null) setUID(it)
            }
        }
    }

    fun setUID(id: String) {
        Log.i(TAG, "setUID: ${uid.hasObservers() || _uid.hasObservers()}")
        _uid.value = id
    }

    fun initializeApp() = initializeAppUseCase.execute()
}