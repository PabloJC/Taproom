package com.pabji.data.repositories

import com.pabji.data.datasources.BeerLocalDatasource
import com.pabji.data.datasources.BeerRemoteDatasource
import com.pabji.domain.api.BeerApiResponse
import com.pabji.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class BeerRepositoryImpl(
    private val localDataSource: BeerLocalDatasource,
    private val remoteDataSource: BeerRemoteDatasource
) : BeerRepository {

    override suspend fun getBeers(max: Int, limit: Int): Flow<List<Beer>> =
        localDataSource.getBeers().onEach { items ->
            val itemsInDB = items.size
            if (itemsInDB < max) {
                val page = (itemsInDB / limit) + 1
                remoteDataSource.getBeers(page).let { newItems ->
                    localDataSource.saveBeers(newItems.filterValidBeers(max, itemsInDB))
                }
            }
        }

    override suspend fun getBeerDetail(id: Long): Flow<Beer> =
        localDataSource.getBeerById(id)

    override suspend fun setEmptyBarrel(id: Long, isEmptyBarrel: Boolean) =
        localDataSource.setBarrelEmptyById(id, isEmptyBarrel)

    private fun List<BeerApiResponse>.filterValidBeers(
        max: Int,
        numCurrentItems: Int
    ): List<BeerApiResponse> {
        val numValidItems = when {
            size >= max -> max
            size >= max - numCurrentItems -> max - numCurrentItems
            else -> size
        }
        return take(numValidItems)
    }
}