package com.pabji.data.repositories

import com.pabji.domain.model.Beer
import com.pabji.domain.model.ItemBeer
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    suspend fun getBeers(): Flow<List<ItemBeer>>
    suspend fun getBeerDetail(id: Long): Flow<Beer>
    suspend fun setEmptyBarrel(id: Long, isEmptyBarrel: Boolean)
}