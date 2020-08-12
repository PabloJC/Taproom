package com.pabji.data.repositories

import com.pabji.data.datasources.BeerLocalDatasource
import com.pabji.data.datasources.BeerRemoteDatasource
import com.pabji.domain.api.MAX_BEERS
import com.pabji.domain.api.PAGE_LIMIT
import com.pabji.domain.model.Beer
import com.pabji.domain.model.ItemBeer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class BeerRepositoryImpl(
    private val localDataSource: BeerLocalDatasource,
    private val remoteDataSource: BeerRemoteDatasource
) : BeerRepository {

    override suspend fun getBeers(): Flow<List<ItemBeer>> = flow {
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

    override suspend fun getBeerDetail(itemBeer: ItemBeer): Beer? =
        localDataSource.getBeerById(itemBeer.id)

    override suspend fun setBeerBarrel(itemBeer: ItemBeer, isEmptyBarrel: Boolean) {
        return localDataSource.setBarrelEmptyById(itemBeer.id, isEmptyBarrel)

    }

}