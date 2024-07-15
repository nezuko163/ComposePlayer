package com.nezuko.composeplayer.app.di

import com.nezuko.data.repositoryImpl.AuthRepositoryImpl
import com.nezuko.data.repositoryImpl.PlayerRepositoryImpl
import com.nezuko.data.repositoryImpl.PlaylistRepositoryImpl
import com.nezuko.domain.repository.PlayerRepository
import com.nezuko.domain.usecase.AddQueueItemUseCase
import com.nezuko.domain.usecase.GetAllLocalTracksUseCase
import com.nezuko.domain.usecase.GetCurrentUserIDUseCase
import com.nezuko.domain.usecase.GetTrackListByPlaylistIdUseCase
import com.nezuko.domain.usecase.InitializeAppUseCase
import com.nezuko.domain.usecase.LoginViaEmailAndPasswordUseCase
import com.nezuko.domain.usecase.NextTrackUseCase
import com.nezuko.domain.usecase.OnServiceStartUseCase
import com.nezuko.domain.usecase.OnServiceStopUseCase
import com.nezuko.domain.usecase.PauseUseCase
import com.nezuko.domain.usecase.PlayUseCase
import com.nezuko.domain.usecase.PreviousTrackUseCase
import com.nezuko.domain.usecase.RegisterViaEmailAndPasswordUseCase
import com.nezuko.domain.usecase.SeekToUseCase
import com.nezuko.domain.usecase.SetPlayerCallbacksUseCase
import com.nezuko.domain.usecase.SkipToQueueItemUseCase
import com.nezuko.domain.usecase.StopUseCase
import org.koin.dsl.module

val domainModule = module {
//    factory<NextTrackUseCase> {
//        NextTrackUseCase(playerRepository = get<PlayerRepositoryImpl>())
//    }
//    factory<PauseMusicUseCase> {
//        PauseMusicUseCase(playerRepository = get<PlayerRepositoryImpl>())
//    }
//    factory<PlayMusicUseCase> {
//        PlayMusicUseCase(playerRepository = get<PlayerRepositoryImpl>())
//    }

    factory<GetCurrentUserIDUseCase> {
        GetCurrentUserIDUseCase(impl = get<AuthRepositoryImpl>())
    }

    factory<RegisterViaEmailAndPasswordUseCase> {
        RegisterViaEmailAndPasswordUseCase(impl = get<AuthRepositoryImpl>())
    }

    factory<GetAllLocalTracksUseCase> {
        GetAllLocalTracksUseCase(impl = get<PlaylistRepositoryImpl>())
    }

    factory<GetTrackListByPlaylistIdUseCase> {
        GetTrackListByPlaylistIdUseCase(impl = get<PlaylistRepositoryImpl>())
    }

    factory<LoginViaEmailAndPasswordUseCase> {
        LoginViaEmailAndPasswordUseCase(impl = get<AuthRepositoryImpl>())
    }

    factory<InitializeAppUseCase> {
        InitializeAppUseCase(impl = get<AuthRepositoryImpl>())
    }

    // PlayerRepository
    factory<PlayUseCase> {
        PlayUseCase(impl = get<PlayerRepositoryImpl>())
    }

    factory<StopUseCase> {
        StopUseCase(impl = get<PlayerRepositoryImpl>())
    }

    factory<PauseUseCase> {
        PauseUseCase(impl = get<PlayerRepositoryImpl>())
    }

    factory<NextTrackUseCase> {
        NextTrackUseCase(impl = get<PlayerRepositoryImpl>())
    }

    factory<PreviousTrackUseCase> {
        PreviousTrackUseCase(impl = get<PlayerRepositoryImpl>())
    }

    factory<SeekToUseCase> {
        SeekToUseCase(impl = get<PlayerRepositoryImpl>())
    }

    factory<OnServiceStopUseCase> {
        OnServiceStopUseCase(impl = get<PlayerRepositoryImpl>())
    }

    factory<OnServiceStartUseCase> {
        OnServiceStartUseCase(impl = get<PlayerRepositoryImpl>())
    }

    factory<SetPlayerCallbacksUseCase> {
        SetPlayerCallbacksUseCase(impl = get<PlayerRepositoryImpl>())
    }

    factory<AddQueueItemUseCase> {
        AddQueueItemUseCase(impl = get<PlayerRepositoryImpl>())
    }

    factory<SkipToQueueItemUseCase> {
        SkipToQueueItemUseCase(impl = get<PlayerRepositoryImpl>())
    }


}