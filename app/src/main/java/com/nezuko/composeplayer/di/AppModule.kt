package com.nezuko.composeplayer.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.nezuko.composeplayer.app.ui.mainScreen.NumbersViewModel
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::NumbersViewModel)
}