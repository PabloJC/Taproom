package com.pabji.taproom.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pabji.domain.mocks.mockBeerListFlow
import com.pabji.taproom.data.uimodel.UIItemBeer
import com.pabji.taproom.data.uimodel.mockUIBeerList
import com.pabji.taproom.ui.common.EventObserver
import com.pabji.usecases.GetBeers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getBeers: GetBeers

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = MainViewModel(getBeers)
    }

    @Test
    fun `while observing beerList LiveData, beer list is shown`() {
        runBlocking {
            whenever(getBeers.invoke()).thenReturn(mockBeerListFlow)
            val firstItem = vm.beerList.first()
            Assert.assertEquals(firstItem, mockUIBeerList)
        }
    }

    @Test
    fun `while observing navigation LiveData, event is launched`() {
        runBlocking {
            val navItem = vm.navigation.first()
            vm.onItemClicked(any())
            Assert.assertEquals(navItem, any())
        }
    }
}
