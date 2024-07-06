package com.nezuko.composeplayer.app.di

import com.nezuko.composeplayer.app.ui.screens.libraryScreen.PlaylistsViewModel
import com.nezuko.composeplayer.app.ui.screens.startScreen.registerScreen.RegisterViewModel
import com.nezuko.composeplayer.app.ui.screens.mainScreen.NumbersViewModel
import com.nezuko.composeplayer.app.ui.screens.libraryScreen.playlistScreen.PlaylistViewModel
import com.nezuko.composeplayer.app.ui.screens.startScreen.loginScreen.LoginViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.domain.usecase.GetAllLocalTracksUseCase
import com.nezuko.domain.usecase.GetCurrentUserIDUseCase
import com.nezuko.domain.usecase.GetTrackListByPlaylistIdUseCase
import com.nezuko.domain.usecase.InitializeAppUseCase
import com.nezuko.domain.usecase.LoginViaEmailAndPasswordUseCase
import com.nezuko.domain.usecase.RegisterViaEmailAndPasswordUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    viewModel<NumbersViewModel> { NumbersViewModel() }
    viewModel<PlaylistsViewModel> { PlaylistsViewModel(get<GetAllLocalTracksUseCase>()) }
    viewModel<UserViewModel> {
        UserViewModel(
            get<GetCurrentUserIDUseCase>(),
            get<InitializeAppUseCase>()
        )
    }
    viewModel<RegisterViewModel> { RegisterViewModel(get<RegisterViaEmailAndPasswordUseCase>()) }
    viewModel<PlaylistViewModel> {
        PlaylistViewModel(
            get<GetTrackListByPlaylistIdUseCase>(),
            get(named("IODispatcher"))
        )
    }
    viewModel<LoginViewModel> {
        LoginViewModel(
            get<LoginViaEmailAndPasswordUseCase>(),
            get(named("IODispatcher"))
        )
    }
}