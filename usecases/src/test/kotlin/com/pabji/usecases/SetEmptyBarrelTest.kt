package com.pabji.usecases

import com.nhaarman.mockitokotlin2.verify
import com.pabji.data.repositories.BeerRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetEmptyBarrelTest {

    @Mock
    lateinit var beerRepository: BeerRepository

    lateinit var setEmptyBarrel: SetEmptyBarrel

    @Before
    fun setUp() {
        setEmptyBarrel = SetEmptyBarrel(beerRepository)
    }

    @Test
    fun `calling invoke should return beer detail`() {
        runBlocking {
            setEmptyBarrel(1, true)
            verify(beerRepository).setEmptyBarrel(1, true)
        }
    }
}