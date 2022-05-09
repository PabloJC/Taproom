package com.pabji.taproom.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.pabji.taproom.data.uimodel.UIItemBeer
import com.pabji.taproom.data.uimodel.mockUIBeerList
import com.pabji.taproom.initMockedDi
import com.pabji.usecases.GetBeers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var uiModelObserver: Observer<List<UIItemBeer>>

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { MainViewModel(get()) }
            factory { GetBeers(get(), MAX_BEERS, API_PAGE_LIMIT) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `while observing beerList LiveData, beer list is shown`() {
        runBlocking {
            val firsItem = vm.beerList.first()
            Assert.assertEquals(firsItem, mockUIBeerList)
        }
    }

    companion object {
        private const val MAX_BEERS = 100
        private const val API_PAGE_LIMIT = 20
    }
}