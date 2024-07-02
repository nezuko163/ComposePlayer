package com.nezuko.composeplayer.app.di

import com.nezuko.data.repositoryImpl.AuthLoginRepositoryImpl
import com.nezuko.data.repositoryImpl.PlaylistsRepositoryImpl
import com.nezuko.data.service.MediaBrowserManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<PlaylistsRepositoryImpl> { PlaylistsRepositoryImpl(context = get()) }
    single<MediaBrowserManager> { MediaBrowserManager(context = get()) }
    single<AuthLoginRepositoryImpl> {
        AuthLoginRepositoryImpl(
            context = get(),
            ioDispatcher = get(named("IODispatcher"))
        )
    }
}