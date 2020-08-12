package com.pabji.domain.model

data class Beer(
    val id: Long,
    val name: String,
    val description: String = "",
    val imageUrl: String = "",
    val abv: Double = 0.0,
    val ibu: Double = 0.0,
    val foodPairing: List<String> = emptyList(),
    val isBarrelEmpty: Boolean = false
)