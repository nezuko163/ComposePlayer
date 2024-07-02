package com.nezuko.composeplayer.app.di

import com.nezuko.composeplayer.app.ui.screens.libraryScreen.PlaylistsViewModel
import com.nezuko.composeplayer.app.ui.screens.loginScreen.AuthViewModel
import com.nezuko.composeplayer.app.ui.screens.mainScreen.NumbersViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.domain.usecase.GetAllPlaylistsFromLocalUseCase
import com.nezuko.domain.usecase.GetAllTracksFromLocalStoreUseCase
import com.nezuko.domain.usecase.GetCurrentUserIDUseCase
import com.nezuko.domain.usecase.RegisterViaPasswordAndEmialUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<NumbersViewModel> { NumbersViewModel() }
    viewModel<PlaylistsViewModel> { PlaylistsViewModel(get<GetAllPlaylistsFromLocalUseCase>(), get<GetAllTracksFromLocalStoreUseCase>()) }
    viewModel<UserViewModel> { UserViewModel(get<GetCurrentUserIDUseCase>()) }
    viewModel<AuthViewModel> { AuthViewModel(get<RegisterViaPasswordAndEmialUseCase>()) }
}