package com.pabji.taproom.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.pabji.data.datasources.BeerLocalDatasource
import com.pabji.taproom.data.uimodel.FakeLocalDataSource
import com.pabji.taproom.data.uimodel.UIBeerDetail
import com.pabji.taproom.data.uimodel.mockUIBeerDetail
import com.pabji.taproom.data.uimodel.mockUIBeerDetailEmptyBarrel
import com.pabji.taproom.initMockedDi
import com.pabji.usecases.GetBeerDetail
import com.pabji.usecases.SetEmptyBarrel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@FlowPreview
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTest : AutoCloseKoinTest() {

    private lateinit var localDataSource: FakeLocalDataSource

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var uiModelObserver: Observer<UIBeerDetail>

    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { DetailViewModel(ID, get(), get(), get()) }
            factory { GetBeerDetail(get()) }
            factory { SetEmptyBarrel(get()) }
        }

        initMockedDi(vmModule)
        localDataSource = get<BeerLocalDatasource>() as FakeLocalDataSource
        vm = get()
    }

    @Test
    fun `while observing model LiveData, beer detail is shown`() {
        vm.model.observeForever(uiModelObserver)
        verify(uiModelObserver, times(1)).onChanged(mockUIBeerDetail)
    }

    @Test
    fun `calling onEmptyBarrelButtonClick, it will change availability`() {
        vm.model.observeForever(uiModelObserver)
        verify(uiModelObserver, times(1)).onChanged(mockUIBeerDetail)
        vm.onEmptyBarrelButtonClick()
        verify(uiModelObserver, times(1)).onChanged(mockUIBeerDetailEmptyBarrel)
    }

    companion object {
        private const val ID = 1L
    }
}