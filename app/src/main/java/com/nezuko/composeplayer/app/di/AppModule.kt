package com.nezuko.composeplayer.app.di

import com.nezuko.composeplayer.app.ui.screens.libraryScreen.PlaylistsViewModel
import com.nezuko.composeplayer.app.ui.screens.loginScreen.AuthViewModel
import com.nezuko.composeplayer.app.ui.screens.mainScreen.NumbersViewModel
import com.nezuko.composeplayer.app.ui.screens.playlistScreen.PlaylistViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.domain.usecase.GetAllLocalTracksUseCase
import com.nezuko.domain.usecase.GetCurrentUserIDUseCase
import com.nezuko.domain.usecase.GetTrackListByPlaylistIdUseCase
import com.nezuko.domain.usecase.RegisterViaEmailAndPasswordUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    viewModel<NumbersViewModel> { NumbersViewModel() }
    viewModel<PlaylistsViewModel> { PlaylistsViewModel(get<GetAllLocalTracksUseCase>()) }
    viewModel<UserViewModel> { UserViewModel(get<GetCurrentUserIDUseCase>()) }
    viewModel<AuthViewModel> { AuthViewModel(get<RegisterViaEmailAndPasswordUseCase>()) }
    viewModel<PlaylistViewModel> {
        PlaylistViewModel(
            get<GetTrackListByPlaylistIdUseCase>(),
            get(named("IODispatcher"))
        )
    }
}