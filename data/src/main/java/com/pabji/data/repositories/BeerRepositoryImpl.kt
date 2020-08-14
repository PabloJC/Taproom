package com.pabji.data.repositories

import com.pabji.data.datasources.BeerLocalDatasource
import com.pabji.data.datasources.BeerRemoteDatasource
import com.pabji.domain.api.MAX_BEERS
import com.pabji.domain.api.PAGE_LIMIT
import com.pabji.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class BeerRepositoryImpl(
    private val localDataSource: BeerLocalDatasource,
    private val remoteDataSource: BeerRemoteDatasource
) : BeerRepository {

    override suspend fun getBeers(): Flow<List<Beer>> = flow {
        localDataSource.getBeers().collect { items ->
            if (items.size < MAX_BEERS) {
                val numPages = (MAX_BEERS / PAGE_LIMIT) + 1
                (1..numPages).forEach {
                    val newBeers = remoteDataSource.getBeers(it)
                    localDataSource.saveBeers(newBeers)
                }
            }
            emit(items)
        }
    }

    override suspend fun getBeerDetail(id: Long): Flow<Beer> =
        localDataSource.getBeerById(id)

    override suspend fun setEmptyBarrel(id: Long, isEmptyBarrel: Boolean) =
        localDataSource.setBarrelEmptyById(id, isEmptyBarrel)
}