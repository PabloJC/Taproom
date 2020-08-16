package com.pabji.taproom.data.uimodel

import android.graphics.Color

val mockUIBeerList = (1..20L).map { UIItemBeer(it, "", "", "", -1) }

val mockUIBeerDetail = UIBeerDetail("", "", "", "", "0.0", "0.0", false, Color.WHITE)
