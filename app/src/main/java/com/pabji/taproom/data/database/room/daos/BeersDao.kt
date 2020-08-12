package com.pabji.taproom.data.database.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pabji.domain.model.Beer
import com.pabji.taproom.data.database.room.entities.BeerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BeersDao {

    @Query("SELECT * FROM beers")
    suspend fun getAll(): Flow<List<BeerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<BeerEntity>)

    @Query("UPDATE beers SET isBarrelEmpty = :isBarrelEmpty WHERE id = :id")
    suspend fun update(id: Long, isBarrelEmpty: Boolean)

    @Query("SELECT COUNT(*) = 0 from beers")
    suspend fun isEmpty(): Boolean

    @Query("SELECT * from beers WHERE id = :id")
    suspend fun getBeerById(id: Long): Beer?
}