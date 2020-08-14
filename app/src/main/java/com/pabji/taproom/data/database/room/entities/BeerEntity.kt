package com.pabji.taproom.data.database.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pabji.domain.api.BeerApiResponse
import com.pabji.domain.model.Beer

@Entity(tableName = "beers")
data class BeerEntity(
    @PrimaryKey
    val id: Long = 0,
    val name: String,
    val tabline: String,
    val description: String,
    val imageUrl: String,
    val abv: Double,
    val ibu: Double,
    val foodPairing: List<String>,
    val isBarrelEmpty: Boolean
)

fun BeerEntity.toBeer() =
    Beer(id, name, description, tabline, imageUrl, abv, ibu, foodPairing, isBarrelEmpty)

fun List<BeerEntity>.toItemBeerList() = map { it.toBeer() }

fun BeerApiResponse.toBeerEntity() = BeerEntity(
    id,
    name ?: "",
    tagline ?: "",
    description ?: "",
    imageUrl ?: "",
    abv ?: 0.0,
    ibu ?: 0.0,
    foodPairing ?: emptyList(),
    false
)

