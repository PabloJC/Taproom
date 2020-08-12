package com.pabji.data.datasources

import com.pabji.domain.model.Beer

interface BeerRemoteDatasource {
    suspend fun getBeers(): List<Beer>
}