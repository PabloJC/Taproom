package com.pabji.taproom.data.database.room.datasources

import com.pabji.data.datasources.BeerLocalDatasource
import com.pabji.domain.api.BeerApiResponse
import com.pabji.domain.model.Beer
import com.pabji.taproom.data.database.room.MyRoomDatabase
import com.pabji.taproom.data.database.room.entities.toBeer
import com.pabji.taproom.data.database.room.entities.toBeerEntity
import com.pabji.taproom.data.database.room.entities.toItemBeerList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BeerRoomDatasource(database: MyRoomDatabase) : BeerLocalDatasource {

    private val beersDao = database.beersDao()

    override suspend fun getBeers(): Flow<List<Beer>> =
        beersDao.getBeers().map { it.toItemBeerList() }

    override suspend fun saveBeers(it: List<BeerApiResponse>) =
        beersDao.insert(it.map { it.toBeerEntity() })

    override suspend fun getBeerById(id: Long): Flow<Beer> =
        beersDao.getBeerById(id).map { it.toBeer() }

    override suspend fun setBarrelEmptyById(id: Long, emptyBarrel: Boolean) =
        beersDao.update(id, emptyBarrel)

}
