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
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getBeers: GetBeers

    @Mock
    lateinit var uiModelObserver: Observer<List<UIItemBeer>>

    @Mock
    lateinit var navigationObserver: EventObserver<Long>

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = MainViewModel(getBeers, Dispatchers.Unconfined)
    }

    @Test
    fun `while observing beerList LiveData, beer list is shown`() {
        runBlocking {
            whenever(getBeers.invoke()).thenReturn(mockBeerListFlow)
            vm.beerList.observeForever(uiModelObserver)
            verify(uiModelObserver).onChanged(mockUIBeerList)
        }
    }

    @Test
    fun `while observing navigation LiveData, event is launched`() {
        runBlocking {
            vm.navigation.observeForever(navigationObserver)
            vm.onItemClicked(any())
            verify(navigationObserver).onChanged(any())
        }
    }
}
