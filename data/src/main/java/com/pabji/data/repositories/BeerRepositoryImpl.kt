package com.pabji.data.repositories

import com.pabji.data.datasources.BeerLocalDatasource
import com.pabji.data.datasources.BeerRemoteDatasource
import com.pabji.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class BeerRepositoryImpl(
    private val localDataSource: BeerLocalDatasource,
    private val remoteDataSource: BeerRemoteDatasource
) : BeerRepository {

    override suspend fun getBeers(limit: Int, max: Int): Flow<List<Beer>> =
        localDataSource.getBeers().onEach { items ->
            if (items.size < max) {
                val numPages = (max / limit) + 1
                (1..numPages).forEach {
                    val newBeers = remoteDataSource.getBeers(it, limit)
                    localDataSource.saveBeers(newBeers)
                }
            }
        }

    override suspend fun getBeerDetail(id: Long): Flow<Beer> =
        localDataSource.getBeerById(id)

    override suspend fun setEmptyBarrel(id: Long, isEmptyBarrel: Boolean) =
        localDataSource.setBarrelEmptyById(id, isEmptyBarrel)
}