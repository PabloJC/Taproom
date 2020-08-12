package com.pabji.taproom.di

import android.app.Application
import androidx.room.Room
import com.pabji.data.datasources.BeerLocalDatasource
import com.pabji.taproom.data.database.room.MyRoomDatabase
import com.pabji.taproom.data.database.room.datasources.BeerRoomDatasource
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
    single {
        Room.databaseBuilder(get(), MyRoomDatabase::class.java, "myDb.db")
            .fallbackToDestructiveMigration().build()
    }
    factory<BeerLocalDatasource> { BeerRoomDatasource(get()) }
}

val scopesModule = module {

    scope(named<MainFragment>()) {
        viewModel { MainViewModel(get()) }
    }
}