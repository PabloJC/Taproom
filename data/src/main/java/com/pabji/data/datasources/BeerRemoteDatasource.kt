package com.pabji.data.datasources

import com.pabji.domain.api.BeerApiResponse

interface BeerRemoteDatasource {
    suspend fun getBeers(offset: Int, limit: Int): List<BeerApiResponse>
}