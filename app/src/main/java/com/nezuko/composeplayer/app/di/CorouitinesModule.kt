package com.nezuko.composeplayer.app.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val corouitinesModule = module {
    single(named("IODispatcher")) {
        Dispatchers.IO
    }


}