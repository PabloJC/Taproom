package com.pabji.taproom.data.network.retrofit.datasources

import com.pabji.data.datasources.BeerRemoteDatasource
import com.pabji.domain.api.BeerApiResponse
import com.pabji.taproom.data.network.retrofit.BeersApiClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class BeerRetrofitDataSource(
    apiClient: BeersApiClient,
    private val dispatcher: CoroutineDispatcher
) : BeerRemoteDatasource {

    private val service = apiClient.service

    override suspend fun getBeers(offset: Int, limit: Int): List<BeerApiResponse> {
        return withContext(dispatcher) {
            service.getBeers(offset, limit).run {
                var response = emptyList<BeerApiResponse>()
                if (isSuccessful) {
                    body()?.let { response = it }
                }
                response
            }
        }
    }

}
