package com.nezuko.di

import com.nezuko.domain.repository.PlayerRepository
import com.nezuko.domain.repository.PlaylistsRepository
import com.nezuko.domain.usecase.GetAllPlaylistsFromLocalUseCase
import com.nezuko.domain.usecase.NextTrackUseCase
import com.nezuko.domain.usecase.PauseMusicUseCase
import com.nezuko.domain.usecase.PlayMusicUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<GetAllPlaylistsFromLocalUseCase> {
        GetAllPlaylistsFromLocalUseCase(playlistsRepository = get<PlaylistsRepository>())
    }

    factory<NextTrackUseCase> {
        NextTrackUseCase(playerRepository = get<PlayerRepository>())
    }
    factory<PauseMusicUseCase> {
        PauseMusicUseCase(playerRepository = get<PlayerRepository>())
    }
    factory<PlayMusicUseCase> {
        PauseMusicUseCase(playerRepository = get<PlayerRepository>())
    }

}