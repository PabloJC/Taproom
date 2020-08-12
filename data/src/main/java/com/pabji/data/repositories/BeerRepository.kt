package com.pabji.data.repositories

import com.pabji.domain.model.Beer
import com.pabji.domain.model.ItemBeer
import kotlinx.coroutines.flow.Flow

interface BeerRepository {
    suspend fun getBeers(): Flow<List<ItemBeer>>
    suspend fun getBeerDetail(itemBeer: ItemBeer): Beer
    suspend fun setBeerBarrel(itemBeer: ItemBeer, isEmptyBarrel: Boolean)
}