package com.pabji.domain.model

data class ItemBeer(
    val id: Long,
    val name: String,
    val tabline: String = "",
    val imageUrl: String = "",
    val isAvailable: Boolean = false
)