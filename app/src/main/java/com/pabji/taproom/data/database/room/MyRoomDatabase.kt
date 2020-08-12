package com.pabji.taproom.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pabji.taproom.data.database.room.daos.BeersDao
import com.pabji.taproom.data.database.room.entities.BeerEntity

@Database(entities = [BeerEntity::class], version = 1)
@TypeConverters(MyTypeConverters::class)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun beersDao(): BeersDao
}