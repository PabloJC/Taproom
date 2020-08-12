package com.pabji.domain.model

data class Beer(
    val name: String,
    val description: String = "",
    val imageUrl: String = "",
    val abv: Double = 0.0,
    val ibu: Int = 0,
    val foodPairing: List<String> = emptyList(),
    val isBarrelEmpty: Boolean = false
)