package com.pabji.data.datasources

import com.pabji.domain.api.BeerApiResponse
import com.pabji.domain.model.Beer
import kotlinx.coroutines.flow.Flow

interface BeerLocalDatasource {
    suspend fun getBeers(): Flow<List<Beer>>
    suspend fun saveBeers(it: List<BeerApiResponse>)
    suspend fun getBeerById(id: Long): Flow<Beer>
    suspend fun setBarrelEmptyById(id: Long, emptyBarrel: Boolean)
}