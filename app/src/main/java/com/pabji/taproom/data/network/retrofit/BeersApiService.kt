package com.pabji.taproom.data.network.retrofit

import com.pabji.domain.api.BeerApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersApiService {

    @GET("beers")
    suspend fun getBeers(
        @Query("page") offset: Int,
        @Query("per_page") limit: Int
    ): Response<List<BeerApiResponse>>
}