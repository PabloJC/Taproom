package com.pabji.data.repositories

import com.pabji.domain.model.Beer
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    suspend fun getBeers(limit: Int, max: Int): Flow<List<Beer>>
    suspend fun getBeerDetail(id: Long): Flow<Beer>
    suspend fun setEmptyBarrel(id: Long, isEmptyBarrel: Boolean)
}