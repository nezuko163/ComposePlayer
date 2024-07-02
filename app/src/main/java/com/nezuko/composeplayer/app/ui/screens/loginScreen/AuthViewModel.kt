package com.nezuko.composeplayer.app.ui.screens.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.domain.usecase.RegisterViaPasswordAndEmialUseCase
import kotlinx.coroutines.launch

class AuthViewModel(
    private val registerViaPasswordAndEmialUseCase: RegisterViaPasswordAndEmialUseCase
) : ViewModel() {
    fun registerViaPasswordAndEmail(email: String, password: String,
                                    onAuthComplete: (String?) -> Unit) {
        viewModelScope.launch {
            registerViaPasswordAndEmialUseCase.execute(email, password, onAuthComplete)
        }
    }
}