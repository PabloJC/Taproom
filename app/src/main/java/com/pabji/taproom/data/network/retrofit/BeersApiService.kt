package com.pabji.taproom.data.network.retrofit

import com.pabji.domain.api.BeerApiResponse
import com.pabji.domain.api.PAGE_LIMIT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BeersApiService {

    @GET("beers")
    suspend fun getBeers(
        @Query("page") offset: Int,
        @Query("per_page") limit: Int = PAGE_LIMIT
    ): Response<List<BeerApiResponse>>
}