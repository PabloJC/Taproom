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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

val mockUIBeerDetailEmptyBarrel = mockBeer.copy(isBarrelEmpty = true).toUIModel()
val mockUIBeerDetail = mockBeer.toUIModel()
val mockUIBeerList = mockBeerList.map { it.toUIItemModel() }

@FlowPreview
@ExperimentalCoroutinesApi
class FakeLocalDataSource : BeerLocalDatasource {

    override fun getBeers(): Flow<List<Beer>> = mockBeerListFlow

    override suspend fun saveBeers(it: List<BeerApiResponse>) = Unit

    override fun getBeerById(id: Long): Flow<Beer> = flow { emit(mockBeer) }

    override suspend fun setBarrelEmptyById(id: Long, emptyBarrel: Boolean) = Unit
}

class FakeRemoteDataSource : BeerRemoteDatasource {
    override suspend fun getBeers(page: Int): List<BeerApiResponse> = mock20BeerApiResponseList
}