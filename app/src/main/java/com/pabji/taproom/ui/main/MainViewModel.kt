package com.pabji.taproom.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.taproom.data.uimodel.UIItemBeer
import com.pabji.taproom.data.uimodel.toUIModelList
import com.pabji.taproom.ui.common.Event
import com.pabji.taproom.ui.common.base.BaseViewModel
import com.pabji.usecases.GetBeers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val getBeers: GetBeers,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val _navigation = MutableLiveData<Event<Long>>()
    val navigation: LiveData<Event<Long>> = _navigation

    private val _beerList = MutableLiveData<List<UIItemBeer>>()
    val beerList: LiveData<List<UIItemBeer>>
        get() {
            if (_beerList.value == null) loadData()
            return _beerList
        }

    fun loadData() {
        launch {
            getBeers().map { it.toUIModelList() }.collect { _beerList.value = it }
        }
    }

    fun onItemClicked(id: Long) {
        _navigation.value = Event(id)
    }
}