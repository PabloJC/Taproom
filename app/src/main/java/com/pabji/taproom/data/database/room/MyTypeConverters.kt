package com.pabji.taproom.data.database.room

import androidx.room.TypeConverter

class MyTypeConverters {

    @TypeConverter
    fun stringToListString(value: String?): List<String> = value?.split(",") ?: emptyList()

    @TypeConverter
    fun listStringToString(value: List<String>?): String = value?.joinToString(",") ?: ""
}