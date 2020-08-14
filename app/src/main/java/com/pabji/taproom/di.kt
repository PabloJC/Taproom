package com.pabji.taproom

import android.app.Application
import androidx.room.Room
import com.pabji.data.datasources.BeerLocalDatasource
import com.pabji.data.datasources.BeerRemoteDatasource
import com.pabji.data.repositories.BeerRepository
import com.pabji.data.repositories.BeerRepositoryImpl
import com.pabji.taproom.data.database.room.MyRoomDatabase
import com.pabji.taproom.data.database.room.datasources.BeerRoomDatasource
import com.pabji.taproom.data.network.retrofit.BeersApiClient
import com.pabji.taproom.data.network.retrofit.datasources.BeerRetrofitDataSource
import com.pabji.taproom.ui.detail.DetailFragment
import com.pabji.taproom.ui.detail.DetailViewModel
import com.pabji.taproom.ui.main.MainFragment
import com.pabji.taproom.ui.main.MainViewModel
import com.pabji.usecases.GetBeerDetail
import com.pabji.usecases.GetBeers
import com.pabji.usecases.SetEmptyBarrel
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
        modules(
            listOf(appModule, dataModule, scopesModule)
        )
    }
}

val appModule = module {
    single<CoroutineDispatcher> { Dispatchers.Main }
    single {
        Room.databaseBuilder(get(), MyRoomDatabase::class.java, "myDb.db")
            .fallbackToDestructiveMigration().build()
    }
    single { BeersApiClient("https://api.punkapi.com/v2/") }

    factory<BeerRemoteDatasource> { BeerRetrofitDataSource(get()) }
    factory<BeerLocalDatasource> { BeerRoomDatasource(get()) }
}

val dataModule = module {
    factory<BeerRepository> { BeerRepositoryImpl(get(), get()) }
}

val scopesModule = module {

    scope(named<MainFragment>()) {
        scoped { GetBeers(get()) }
        viewModel { MainViewModel(get(), get()) }
    }

    scope(named<DetailFragment>()) {
        viewModel { (id: Long) ->
            DetailViewModel(id, get(), get(), get())
        }
        scoped { GetBeerDetail(get()) }
        scoped { SetEmptyBarrel(get()) }
    }
}