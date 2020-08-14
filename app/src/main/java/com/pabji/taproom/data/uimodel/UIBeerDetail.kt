package com.pabji.taproom.data.uimodel

import androidx.annotation.StringRes
import com.pabji.domain.model.Beer
import com.pabji.taproom.R

data class UIBeerDetail(
    val imageUrl: String,
    val name: String,
    val description: String,
    val foodPairing: String,
    val abv: String,
    val ibu: String,
    val isNotAvailable: Boolean,
    @StringRes val buttonTextRes: Int
)

fun Beer.toUIModel() = UIBeerDetail(
    imageUrl,
    name,
    description,
    foodPairing.joinToString("") { "$it\n" },
    "$abv",
    "$ibu",
    isBarrelEmpty,
    if (isBarrelEmpty) R.string.no_empty_barrel else R.string.empty_barrel
)

