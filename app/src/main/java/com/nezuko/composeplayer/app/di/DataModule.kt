package com.nezuko.composeplayer.app.di

import android.app.Activity
import com.nezuko.data.repositoryImpl.AuthRepositoryImpl
import com.nezuko.data.repositoryImpl.PlayerRepositoryImpl
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
    single<AuthRepositoryImpl> {
        AuthRepositoryImpl(
            context = get(),
            ioDispatcher = get(named("IODispatcher"))
        )
    }
    single<PlayerRepositoryImpl> { (activity: Activity) ->
        PlayerRepositoryImpl(context = get(), activity)
    }
}