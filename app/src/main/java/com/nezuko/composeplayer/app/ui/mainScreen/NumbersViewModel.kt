package com.nezuko.composeplayer.app.ui.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NumbersViewModel : ViewModel() {
    private val _numb1 = MutableLiveData<Int?>()
    private val _numb2 = MutableLiveData<Int?>()
    private val _sum = MutableLiveData<Int?>()

    val numb1: LiveData<Int?>
        get() = _numb1
    val numb2: LiveData<Int?>
        get() = _numb2
    val sum: LiveData<Int?>
        get() = _sum

    fun setNumb(string: String, isFirst: Boolean) {
        viewModelScope.launch {
            val numb = try {
                string.toInt()
            } catch (e: Exception) {
                null
            }
            if (isFirst) {
                _numb1.value = numb
            } else {
                _numb2.value = numb
            }
            updateSum()
        }
    }

    private fun updateSum() {
        if (_numb1.value == null || _numb2.value == null) {
            _sum.value = null
            return
        }
        _sum.value = _numb1.value!! + _numb2.value!!
    }
}