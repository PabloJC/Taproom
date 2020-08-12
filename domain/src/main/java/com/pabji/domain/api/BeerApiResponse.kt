package com.pabji.domain.api

import com.google.gson.annotations.SerializedName

data class BeerApiResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("abv") val abv: Double?,
    @SerializedName("ibu") val ibu: Double?,
    @SerializedName("food_pairing") val foodPairing: List<String>?
)
