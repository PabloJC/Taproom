package com.pabji.taproom.di

import android.app.Application
import com.pabji.taproom.ui.main.MainFragment
import com.pabji.taproom.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidContext(this@initDI)
        modules(listOf(appModule, scopesModule))
    }
}

val appModule = module {
    single<CoroutineDispatcher> { Dispatchers.Main }
}

val scopesModule = module {

    scope(named<MainFragment>()) {
        viewModel { MainViewModel(get()) }
    }
}