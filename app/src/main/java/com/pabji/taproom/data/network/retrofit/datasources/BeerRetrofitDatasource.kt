package com.pabji.taproom.data.network.retrofit.datasources

import com.pabji.data.datasources.BeerRemoteDatasource
import com.pabji.domain.api.BeerApiResponse
import com.pabji.taproom.data.network.retrofit.BeersApiClient

class BeerRetrofitDataSource(apiClient: BeersApiClient) : BeerRemoteDatasource {

    private val service = apiClient.service

    override suspend fun getBeers(): List<BeerApiResponse> = service.getAllBeers()

}
