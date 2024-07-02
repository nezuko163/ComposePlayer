package com.nezuko.di

import com.nezuko.composeplayer.app.ui.libraryScreen.PlaylistsViewModel
import com.nezuko.composeplayer.app.ui.mainScreen.NumbersViewModel
import com.nezuko.domain.usecase.GetAllPlaylistsFromLocalUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<NumbersViewModel> { NumbersViewModel() }
    viewModel<PlaylistsViewModel> { PlaylistsViewModel( get<GetAllPlaylistsFromLocalUseCase>() ) }
}