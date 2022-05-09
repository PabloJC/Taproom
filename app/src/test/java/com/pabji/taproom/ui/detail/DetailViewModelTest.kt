package com.pabji.taproom.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pabji.domain.mocks.mockBeer
import com.pabji.domain.mocks.mockBeerFlow
import com.pabji.taproom.data.uimodel.UIBeerDetail
import com.pabji.taproom.data.uimodel.toUIModel
import com.pabji.usecases.GetBeerDetail
import com.pabji.usecases.SetEmptyBarrel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getBeerDetail: GetBeerDetail

    @Mock
    lateinit var setEmptyBarrel: SetEmptyBarrel

    @Mock
    lateinit var uiModelObserver: Observer<UIBeerDetail>

    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {
        vm = DetailViewModel(ID, getBeerDetail, setEmptyBarrel)
    }

    @Test
    fun `while observing model LiveData, beer detail is shown`() {
        runBlocking {
            whenever(getBeerDetail.invoke(ID)).thenReturn(mockBeerFlow)
            vm.model.observeForever(uiModelObserver)
            verify(uiModelObserver).onChanged(mockBeer.toUIModel())
        }
    }

    @Test
    fun `calling onEmptyBarrelButtonClick, it will change availability`() {
        runBlocking {
            val beerDetail = mockBeer.toUIModel()
            vm.onEmptyBarrelButtonClick()
            verify(setEmptyBarrel).invoke(ID, !beerDetail.isNotAvailable)
        }
    }

    companion object {
        const val ID = 1L
    }
}