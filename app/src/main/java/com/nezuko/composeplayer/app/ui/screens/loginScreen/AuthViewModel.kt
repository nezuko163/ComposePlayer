package com.nezuko.composeplayer.app.ui.screens.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.domain.usecase.RegisterViaEmailAndPasswordUseCase
import kotlinx.coroutines.launch

class AuthViewModel(
    private val registerViaEmailAndPasswordUseCase: RegisterViaEmailAndPasswordUseCase
) : ViewModel() {
    fun registerViaPasswordAndEmail(email: String, password: String,
                                    onAuthComplete: (String?) -> Unit) {
        viewModelScope.launch {
            registerViaEmailAndPasswordUseCase.execute(email, password, onAuthComplete)
        }
    }
}