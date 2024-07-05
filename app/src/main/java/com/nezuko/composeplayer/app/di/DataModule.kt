package com.nezuko.composeplayer.app.di

import com.nezuko.data.repositoryImpl.AuthRepositoryImpl
import com.nezuko.data.repositoryImpl.PlaylistRepositoryImpl
import com.nezuko.data.service.MediaBrowserManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<PlaylistRepositoryImpl> {
        PlaylistRepositoryImpl(
            context = get(),
            ioDispatcher = get(named("IODispatcher"))
        )
    }
    single<MediaBrowserManager> { MediaBrowserManager(context = get()) }
    single<AuthRepositoryImpl> {
        AuthRepositoryImpl(
            context = get(),
            ioDispatcher = get(named("IODispatcher"))
        )
    }
}