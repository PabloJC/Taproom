package com.pabji.data.repositories

import com.pabji.data.datasources.BeerLocalDatasource
import com.pabji.data.datasources.BeerRemoteDatasource
import com.pabji.domain.model.Beer
import com.pabji.domain.model.ItemBeer
import kotlinx.coroutines.flow.Flow

class BeerRepositoryImpl(
    private val localDataSource: BeerLocalDatasource,
    private val remoteDataSource: BeerRemoteDatasource
) : BeerRepository {

    override suspend fun getBeers(): Flow<List<ItemBeer>> {
        if (!localDataSource.existBeers()) {
            val newBeers = remoteDataSource.getBeers()
            localDataSource.saveBeers(newBeers)
        }
        return localDataSource.getBeers()
    }

    override suspend fun getBeerDetail(itemBeer: ItemBeer): Beer? =
        localDataSource.getBeerById(itemBeer.id)

    override suspend fun setBeerBarrel(itemBeer: ItemBeer, isEmptyBarrel: Boolean) {
        return localDataSource.setBarrelEmptyById(itemBeer.id, isEmptyBarrel)

    }

}