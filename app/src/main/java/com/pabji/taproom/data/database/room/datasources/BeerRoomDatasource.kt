package com.pabji.taproom.data.database.room.datasources

import com.pabji.data.datasources.BeerLocalDatasource
import com.pabji.domain.model.Beer
import com.pabji.domain.model.ItemBeer
import com.pabji.taproom.data.database.room.MyRoomDatabase
import com.pabji.taproom.data.database.room.entities.toBeerEntity
import com.pabji.taproom.data.database.room.entities.toItemBeerList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BeerRoomDatasource(database: MyRoomDatabase) : BeerLocalDatasource {

    private val beersDao = database.beersDao()

    override suspend fun existBeers(): Boolean = !beersDao.isEmpty()

    override suspend fun getBeers(): Flow<List<ItemBeer>> =
        beersDao.getAll().map { it.toItemBeerList() }

    override suspend fun saveBeers(it: List<Beer>) = beersDao.insert(it.map { it.toBeerEntity() })

    override suspend fun getBeerById(id: Long): Beer? = beersDao.getBeerById(id)

    override suspend fun setBarrelEmptyById(id: Long, emptyBarrel: Boolean) =
        beersDao.update(id, emptyBarrel)
}
