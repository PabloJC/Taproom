package com.pabji.taproom.data.uimodel

import com.pabji.data.datasources.BeerLocalDatasource
import com.pabji.data.datasources.BeerRemoteDatasource
import com.pabji.domain.api.BeerApiResponse
import com.pabji.domain.mocks.mock20BeerApiResponseList
import com.pabji.domain.mocks.mockBeer
import com.pabji.domain.mocks.mockBeerList
import com.pabji.domain.mocks.mockBeerListFlow
import com.pabji.domain.model.Beer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

val mockUIBeerDetailEmptyBarrel = mockBeer.copy(isBarrelEmpty = true).toUIModel()
val mockUIBeerDetail = mockBeer.toUIModel()
val mockUIBeerList = mockBeerList.map { it.toUIItemModel() }

@FlowPreview
@ExperimentalCoroutinesApi
class FakeLocalDataSource : BeerLocalDatasource {

    var channel = ConflatedBroadcastChannel<Beer>()

    override suspend fun getBeers(): Flow<List<Beer>> = mockBeerListFlow

    override suspend fun saveBeers(it: List<BeerApiResponse>) = Unit

    override suspend fun getBeerById(id: Long): Flow<Beer> {
        channel.offer(mockBeer)
        return channel.asFlow()
    }

    override suspend fun setBarrelEmptyById(id: Long, emptyBarrel: Boolean) {
        channel.offer(mockBeer.copy(isBarrelEmpty = true))
    }
}

class FakeRemoteDataSource : BeerRemoteDatasource {
    override suspend fun getBeers(page: Int): List<BeerApiResponse> = mock20BeerApiResponseList
}