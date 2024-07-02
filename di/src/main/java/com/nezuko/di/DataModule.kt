package com.nezuko.di

import com.nezuko.data.repositoryImpl.PlaylistsRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single<PlaylistsRepositoryImpl> { PlaylistsRepositoryImpl(context = get() ) }
}