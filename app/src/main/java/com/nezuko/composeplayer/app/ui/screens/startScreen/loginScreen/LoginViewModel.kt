package com.nezuko.composeplayer.app.ui.screens.startScreen.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.domain.usecase.LoginViaEmailAndPasswordUseCase
import com.nezuko.domain.usecase.RegisterViaEmailAndPasswordUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginViewModel(
    private val loginViaEmailAndPasswordUseCase: LoginViaEmailAndPasswordUseCase,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    fun loginViaEmailAndPassword(email: String, password: String, onLoginComplete: (String) -> Unit) {
        viewModelScope.launch(ioDispatcher) {
            loginViaEmailAndPasswordUseCase.execute(email, password, onLoginComplete)
        }
    }
}