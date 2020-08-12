package com.pabji.taproom.data.network.retrofit

import com.pabji.domain.api.BeerApiResponse
import retrofit2.http.GET

interface BeersApiService {

    @GET("beers")
    suspend fun getAllBeers(): List<BeerApiResponse>
}