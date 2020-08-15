package com.pabji.taproom.data.network.retrofit.datasources

import com.pabji.data.datasources.BeerRemoteDatasource
import com.pabji.domain.api.BeerApiResponse
import com.pabji.taproom.API_PAGE_LIMIT
import com.pabji.taproom.data.network.retrofit.BeersApiClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.get

class BeerRetrofitDataSource(
    apiClient: BeersApiClient,
    private val dispatcher: CoroutineDispatcher
) : BeerRemoteDatasource, KoinComponent {

    private val service = apiClient.service

    override suspend fun getBeers(page: Int): List<BeerApiResponse> =
        withContext(dispatcher) {
            service.getBeers(page, get(API_PAGE_LIMIT)).run {
                if (isSuccessful) {
                    body() ?: emptyList()
                } else {
                    emptyList()
                }
            }
        }
}
