package com.nezuko.composeplayer.app.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class ShouldShowBottomBarViewModel: ViewModel() {
    private val TAG = "ShouldShowBottomBarViewModel"
    private val _shouldShow = MutableLiveData(true)
    val shouldShow: LiveData<Boolean>
        get() = _shouldShow

    fun update(value: Boolean) {
        Log.i(TAG, "update: $value")
        _shouldShow.value = value
    }
}

object ShouldShowBottpmBarVMStoreOwner : ViewModelStoreOwner {
    private val _viewModelStore = ViewModelStore()

    override val viewModelStore: ViewModelStore
        get() = _viewModelStore
}