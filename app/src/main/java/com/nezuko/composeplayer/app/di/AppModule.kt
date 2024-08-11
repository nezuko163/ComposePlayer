package com.nezuko.composeplayer.app.di

import com.nezuko.composeplayer.app.ui.screens.libraryScreen.PlaylistsViewModel
import com.nezuko.composeplayer.app.ui.screens.startScreen.registerScreen.RegisterViewModel
import com.nezuko.composeplayer.app.ui.screens.mainScreen.NumbersViewModel
import com.nezuko.composeplayer.app.ui.screens.libraryScreen.playlistScreen.PlaylistViewModel
import com.nezuko.composeplayer.app.ui.screens.profileScreen.ProfileViewModel
import com.nezuko.composeplayer.app.ui.screens.searchScreen.AsdViewModel
import com.nezuko.composeplayer.app.ui.screens.startScreen.loginScreen.LoginViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.ShouldShowBottomBarViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.domain.usecase.AddQueueItemUseCase
import com.nezuko.domain.usecase.CheckIsEmailFreeUseCase
import com.nezuko.domain.usecase.ClearQueueUseCase
import com.nezuko.domain.usecase.GetAllLocalTracksUseCase
import com.nezuko.domain.usecase.GetTrackListByPlaylistIdUseCase
import com.nezuko.domain.usecase.LoginViaEmailAndPasswordUseCase
import com.nezuko.domain.usecase.NextTrackUseCase
import com.nezuko.domain.usecase.OnServiceStartUseCase
import com.nezuko.domain.usecase.OnServiceStopUseCase
import com.nezuko.domain.usecase.PauseUseCase
import com.nezuko.domain.usecase.PlayOrPauseUseCase
import com.nezuko.domain.usecase.PlayUseCase
import com.nezuko.domain.usecase.PreviousTrackUseCase
import com.nezuko.domain.usecase.RegisterUserProfileUseCase
import com.nezuko.domain.usecase.RegisterViaEmailAndPasswordUseCase
import com.nezuko.domain.usecase.SeekToUseCase
import com.nezuko.domain.usecase.SetPlayerCallbacksUseCase
import com.nezuko.domain.usecase.SkipToQueueItemUseCase
import com.nezuko.domain.usecase.StopUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    viewModel<NumbersViewModel> { NumbersViewModel() }
    viewModel<PlaylistsViewModel> { PlaylistsViewModel(get<GetAllLocalTracksUseCase>()) }
    viewModel<UserViewModel> {
        UserViewModel(
            getCurrentUserIDUseCase = get(),
            initializeAppUseCase = get(),
            getUserProfileByUIDUseCase = get(),
            setAvatarToUserUseCase = get(),
            get(named("IODispatcher"))
        )
    }
    viewModel<RegisterViewModel> {
        RegisterViewModel(
            get<CheckIsEmailFreeUseCase>(),
            get(named("IODispatcher")),
            get<RegisterViaEmailAndPasswordUseCase>(),
            get<RegisterUserProfileUseCase>()
        )
    }
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

    viewModel<PlayerServiceViewModel> {
        PlayerServiceViewModel(
            play = get<PlayUseCase>(),
            stop = get<StopUseCase>(),
            pause = get<PauseUseCase>(),
            next = get<NextTrackUseCase>(),
            previous = get<PreviousTrackUseCase>(),
            seekTo = get<SeekToUseCase>(),
            onServiceStartUseCase = get<OnServiceStartUseCase>(),
            onServiceStopUseCase = get<OnServiceStopUseCase>(),
            setPlayerCallbacksUseCase = get<SetPlayerCallbacksUseCase>(),
            skipToQueueItemUseCase = get<SkipToQueueItemUseCase>(),
            addQueueItemUseCase = get<AddQueueItemUseCase>(),
            clearQueueUseCase = get<ClearQueueUseCase>(),
            playOrPauseUseCase = get<PlayOrPauseUseCase>()
        )
    }

    viewModel<ShouldShowBottomBarViewModel> { ShouldShowBottomBarViewModel() }

    viewModel<AsdViewModel> { AsdViewModel() }

    viewModel<ProfileViewModel> {
        ProfileViewModel(
            setAvatarToUserUseCase = get(),
            getImageFromStorageByUrlUseCase = get(),
            ioDispatcher = get(named("IODispatcher"))
        )
    }
}