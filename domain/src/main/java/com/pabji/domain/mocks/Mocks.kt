package com.pabji.domain.mocks

import com.pabji.domain.api.BeerApiResponse
import com.pabji.domain.model.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

val mock20BeerApiResponseList: List<BeerApiResponse> =
    (1..20L).map { BeerApiResponse(it, "", "", "", "", 0.0, 0.0, emptyList()) }

val mockBeer = Beer(1, "")
val mockBeerFlow: Flow<Beer> = listOf(mockBeer).asFlow()

val mockBeerList = (1..20L).map { Beer(it, "") }
val mockBeerListFlow = listOf(mockBeerList).asFlow()
