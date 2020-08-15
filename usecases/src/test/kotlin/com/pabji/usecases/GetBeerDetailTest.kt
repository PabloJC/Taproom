package com.pabji.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.pabji.data.repositories.BeerRepository
import com.pabji.domain.mocks.mockBeer
import com.pabji.domain.mocks.mockBeerFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetBeerDetailTest {

    @Mock
    lateinit var beerRepository: BeerRepository

    lateinit var getBeerDetail: GetBeerDetail

    @Before
    fun setUp() {
        getBeerDetail = GetBeerDetail(beerRepository)
    }

    @Test
    fun `calling invoke should return beer detail`() {
        runBlocking {
            whenever(beerRepository.getBeerDetail(any())).thenReturn(mockBeerFlow)

            val result = getBeerDetail(1)
            result.collect { assert(mockBeer == it) }
        }
    }
}