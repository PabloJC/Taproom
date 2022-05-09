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

val MAX_BEERS = named("MAX_BEERS")
val API_PAGE_LIMIT = named("API_PAGE_LIMIT")
val DISPATCHER_IO = named("DISPATCHER_IO")

val BASE_URL = named("BASE_URL")

val appModule = module {

    single(BASE_URL) { "https://api.punkapi.com/v2/" }

    single {
        Room.databaseBuilder(get(), MyRoomDatabase::class.java, "myDb.db")
            .fallbackToDestructiveMigration().build()
    }
    single { BeersApiClient(get(BASE_URL)) }

    factory<BeerRemoteDatasource> { BeerRetrofitDataSource(get()) }
    factory<BeerLocalDatasource> { BeerRoomDatasource(get()) }
}

val dataModule = module {
    factory<BeerRepository> { BeerRepositoryImpl(get(), get(), get(DISPATCHER_IO)) }
}

val scopesModule = module {

    single<CoroutineDispatcher>(DISPATCHER_IO) { Dispatchers.IO }

    factory(MAX_BEERS) { 20 }
    factory(API_PAGE_LIMIT) { 80 }

    scope(named<MainFragment>()) {
        viewModel { MainViewModel(get()) }
        scoped { GetBeers(get(), get(MAX_BEERS), get(API_PAGE_LIMIT)) }
    }

    scope(named<DetailFragment>()) {
        viewModel { (id: Long) ->
            DetailViewModel(id, get(), get())
        }
        scoped { GetBeerDetail(get()) }
        scoped { SetEmptyBarrel(get()) }
    }
}