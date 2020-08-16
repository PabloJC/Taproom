package com.pabji.taproom

import com.pabji.data.datasources.BeerLocalDatasource
import com.pabji.data.datasources.BeerRemoteDatasource
import com.pabji.taproom.data.uimodel.FakeLocalDataSource
import com.pabji.taproom.data.uimodel.FakeRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single<BeerLocalDatasource> { FakeLocalDataSource() }
    single<BeerRemoteDatasource> { FakeRemoteDataSource() }
    single<CoroutineDispatcher> { Dispatchers.Unconfined }
}