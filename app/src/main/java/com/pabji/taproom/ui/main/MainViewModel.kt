package com.pabji.taproom.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pabji.taproom.data.uimodel.UIItemBeer
import com.pabji.taproom.data.uimodel.toUIModelList
import com.pabji.usecases.GetBeers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getBeers: GetBeers
) : ViewModel() {

    private val _navigation = Channel<Long>()
    val navigation: Flow<Long> = _navigation.receiveAsFlow()

    val beerList: Flow<List<UIItemBeer>> get() = getBeers().map { it.toUIModelList() }

    fun onItemClicked(id: Long) {
        viewModelScope.launch {
            _navigation.send(id)
        }
    }
}