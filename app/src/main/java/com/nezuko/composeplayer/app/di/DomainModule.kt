package com.nezuko.composeplayer.app.di

import com.nezuko.data.repositoryImpl.AuthRepositoryImpl
import com.nezuko.data.repositoryImpl.PlaylistRepositoryImpl
import com.nezuko.domain.usecase.GetAllLocalTracksUseCase
import com.nezuko.domain.usecase.GetCurrentUserIDUseCase
import com.nezuko.domain.usecase.GetTrackListByPlaylistIdUseCase
import com.nezuko.domain.usecase.InitializeAppUseCase
import com.nezuko.domain.usecase.LoginViaEmailAndPasswordUseCase
import com.nezuko.domain.usecase.RegisterViaEmailAndPasswordUseCase
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
}