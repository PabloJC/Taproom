package com.pabji.taproom.data.database.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pabji.domain.model.Beer
import com.pabji.domain.model.ItemBeer

@Entity(tableName = "beers")
data class BeerEntity(
    @PrimaryKey
    val id: Long = 0,
    val name: String,
    val tabline: String,
    val description: String,
    val imageUrl: String,
    val abv: Double,
    val ibu: Int,
    val foodPairing: List<String>,
    val isBarrelEmpty: Boolean
)

fun BeerEntity.toItemBeer() = ItemBeer(id, name, tabline, imageUrl, !isBarrelEmpty)
fun List<BeerEntity>.toItemBeerList() = map { it.toItemBeer() }

fun BeerEntity.toBeer() =
    Beer(id, name, tabline, description, imageUrl, abv, ibu, foodPairing, isBarrelEmpty)

fun Beer.toBeerEntity() =
    BeerEntity(id, name, tabline, description, imageUrl, abv, ibu, foodPairing, isBarrelEmpty)

