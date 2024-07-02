package com.nezuko.composeplayer.app.di

import com.nezuko.data.repositoryImpl.AuthLoginRepositoryImpl
import com.nezuko.data.repositoryImpl.PlayerRepositoryImpl
import com.nezuko.data.repositoryImpl.PlaylistsRepositoryImpl
import com.nezuko.domain.repository.PlayerRepository
import com.nezuko.domain.repository.PlaylistsRepository
import com.nezuko.domain.usecase.GetAllPlaylistsFromLocalUseCase
import com.nezuko.domain.usecase.GetCurrentUserIDUseCase
import com.nezuko.domain.usecase.NextTrackUseCase
import com.nezuko.domain.usecase.PauseMusicUseCase
import com.nezuko.domain.usecase.PlayMusicUseCase
import com.nezuko.domain.usecase.RegisterViaPasswordAndEmialUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factory<GetAllPlaylistsFromLocalUseCase> {
        GetAllPlaylistsFromLocalUseCase(playlistsRepository = get<PlaylistsRepositoryImpl>())
    }

    factory<NextTrackUseCase> {
        NextTrackUseCase(playerRepository = get<PlayerRepositoryImpl>())
    }
    factory<PauseMusicUseCase> {
        PauseMusicUseCase(playerRepository = get<PlayerRepositoryImpl>())
    }
    factory<PlayMusicUseCase> {
        PlayMusicUseCase(playerRepository = get<PlayerRepositoryImpl>())
    }

    factory<GetCurrentUserIDUseCase> {
        GetCurrentUserIDUseCase(impl = get<AuthLoginRepositoryImpl>())
    }

    factory<RegisterViaPasswordAndEmialUseCase> {
        RegisterViaPasswordAndEmialUseCase(impl = get<AuthLoginRepositoryImpl>())
    }
}