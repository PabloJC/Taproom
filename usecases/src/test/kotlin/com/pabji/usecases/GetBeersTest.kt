package com.pabji.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pabji.data.repositories.BeerRepository
import com.pabji.domain.mocks.mockBeerList
import com.pabji.domain.mocks.mockBeerListFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetBeersTest {

    @Mock
    lateinit var beerRepository: BeerRepository

    lateinit var getBeers: GetBeers

    @Before
    fun setUp() {
        getBeers = GetBeers(beerRepository, MAX_BEERS, API_PAGE_LIMIT)
    }

    @Test
    fun `calling invoke should return beer detail`() {
        runBlocking {
            whenever(beerRepository.getBeers(any(), any())).thenReturn(mockBeerListFlow)

            val result = getBeers()
            verify(beerRepository).getBeers(MAX_BEERS, API_PAGE_LIMIT)
            result.collect { assert(mockBeerList == it) }
        }
    }

    companion object {
        private const val MAX_BEERS = 100
        private const val API_PAGE_LIMIT = 20
    }
}