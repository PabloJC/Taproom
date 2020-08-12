package com.pabji.data.datasources

import com.pabji.domain.model.Beer
import com.pabji.domain.model.ItemBeer
import kotlinx.coroutines.flow.Flow

interface BeerLocalDatasource {
    fun existBeers(): Boolean
    fun getBeers(): Flow<List<ItemBeer>>
    fun saveBeers(it: List<Beer>)
    fun getBeerById(id: Long): Beer
    fun setBarrelEmptyById(id: Long, emptyBarrel: Boolean)
}