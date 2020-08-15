package com.pabji.data.repositories

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pabji.data.datasources.BeerLocalDatasource
import com.pabji.data.datasources.BeerRemoteDatasource
import com.pabji.domain.mocks.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BeerRepositoryImplTest {

    @Mock
    lateinit var localDataSource: BeerLocalDatasource

    @Mock
    lateinit var remoteDatasource: BeerRemoteDatasource

    private lateinit var beerRepository: BeerRepository

    @Before
    fun setUp() {
        beerRepository = BeerRepositoryImpl(localDataSource, remoteDatasource)
    }

    @Test
    fun `calling getBeers with max and limit should set local and return flow`() {
        runBlocking {
            whenever(localDataSource.getBeers()).thenReturn(mockBeerListFlow)
            whenever(remoteDatasource.getBeers(any())).thenReturn(mock20BeerApiResponseList)

            val result = beerRepository.getBeers(MAX_BEERS, API_PAGE_LIMIT)

            result.collect { assert(mockBeerList == it) }
            verify(localDataSource).saveBeers(any())
        }
    }

    @Test
    fun `calling getBeerDetail should return flow`() {
        runBlocking {
            whenever(localDataSource.getBeerById(any())).thenReturn(mockBeerFlow)

            val result = beerRepository.getBeerDetail(any())

            result.collect { assert(mockBeer == it) }
        }
    }

    @Test
    fun `calling setEmptyBarrel should set local`() {
        runBlocking {
            beerRepository.setEmptyBarrel(any(), any())
            verify(localDataSource).setBarrelEmptyById(any(), any())
        }
    }

    companion object {
        private const val MAX_BEERS = 100
        private const val API_PAGE_LIMIT = 20
    }
}