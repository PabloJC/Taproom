package com.pabji.taproom.data.uimodel

import android.graphics.Color
import androidx.annotation.ColorInt
import com.pabji.domain.model.Beer

data class UIItemBeer(
    val id: Long,
    val name: String,
    val tabline: String,
    val imageUrl: String,
    @ColorInt val backgroundColorRes: Int
)

fun Beer.toUIItemModel() =
    UIItemBeer(id, name, tabline, imageUrl, if (isBarrelEmpty) Color.LTGRAY else Color.WHITE)

fun List<Beer>.toUIModelList() = map { it.toUIItemModel() }