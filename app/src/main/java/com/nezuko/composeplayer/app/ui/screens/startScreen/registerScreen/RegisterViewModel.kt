package com.nezuko.composeplayer.app.ui.screens.startScreen.registerScreen

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.composeplayer.app.utils.getGlobalViewModel
import com.nezuko.domain.model.UserProfile
import com.nezuko.domain.usecase.CheckIsEmailFreeUseCase
import com.nezuko.domain.usecase.RegisterUserProfileUseCase
import com.nezuko.domain.usecase.RegisterViaEmailAndPasswordUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val checkIsEmailFreeUseCase: CheckIsEmailFreeUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val registerViaEmailAndPasswordUseCase: RegisterViaEmailAndPasswordUseCase,
    private val registerUserProfileUseCase: RegisterUserProfileUseCase
) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun isEmailCorrect(
        email: String,
        onEmailCheckedComplete: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch(ioDispatcher) {
            checkIsEmailFreeUseCase.execute(email, onEmailCheckedComplete, onFailure)
        }

    }

    fun registerViaPasswordAndEmail(
        onAuthComplete: (String?) -> Unit
    ) {
        viewModelScope.launch(ioDispatcher) {
            if (email.value == null) return@launch
            if (password.value == null) return@launch
            registerViaEmailAndPasswordUseCase.execute(
                email.value!!,
                password.value!!,
                onAuthComplete
            )
        }
    }

    fun registerProfile(
        user: UserProfile, onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        viewModelScope.launch(ioDispatcher) {
            registerUserProfileUseCase.execute(user, onSuccess, onFailure)
        }
    }
}

object RegisterViewModelStoreOwner : ViewModelStoreOwner {
    private val _viewModelStore = ViewModelStore()

    override val viewModelStore: ViewModelStore
        get() = _viewModelStore
}

@Composable
fun getRegisterViewModel() = getGlobalViewModel(
    viewModelClass = RegisterViewModel::class.java,
    storeOwner = RegisterViewModelStoreOwner
)