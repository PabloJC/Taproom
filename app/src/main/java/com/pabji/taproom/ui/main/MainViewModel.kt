package com.pabji.taproom.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.domain.model.ItemBeer
import com.pabji.taproom.ui.common.BaseViewModel
import com.pabji.taproom.ui.common.Event
import com.pabji.usecases.GetBeers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val getBeers: GetBeers,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val _navigation = MutableLiveData<Event<Long>>()
    val navigation: LiveData<Event<Long>> = _navigation

    private val _beerList = MutableLiveData<List<ItemBeer>>()
    val beerList: LiveData<List<ItemBeer>>
        get() {
            if (_beerList.value == null) loadData()
            return _beerList
        }

    fun loadData() {
        launch {
            getBeers().collect { _beerList.value = it }
        }
    }

    fun onItemClicked(item: ItemBeer) {
        _navigation.value = Event(item.id)
    }
}