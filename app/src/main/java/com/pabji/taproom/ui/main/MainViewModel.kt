package com.pabji.taproom.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.taproom.model.ItemBeer
import com.pabji.taproom.ui.common.BaseViewModel
import com.pabji.taproom.ui.common.Event
import kotlinx.coroutines.CoroutineDispatcher

class MainViewModel(
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val _navigation = MutableLiveData<Event<ItemBeer>>()
    val navigation: LiveData<Event<ItemBeer>> = _navigation

    private val _beerList = MutableLiveData<List<ItemBeer>>()
    val beerList : LiveData<List<ItemBeer>>
            get() {
                if (_beerList.value == null) loadData()
                return _beerList
            }

    fun loadData(){
        _beerList.value = listOf(ItemBeer("Beer1","Tab1","https://www.bodecall.com/images/stories/virtuemart/product/cruzcampo-botella-33.png",true),
            ItemBeer("Beer2","Tab2","https://www.bodecall.com/images/stories/virtuemart/product/cruzcampo-botella-33.png",false),
            ItemBeer("Beer3","Tab3","https://www.bodecall.com/images/stories/virtuemart/product/cruzcampo-botella-33.png",false),
            ItemBeer("Beer4","Tab4","https://www.bodecall.com/images/stories/virtuemart/product/cruzcampo-botella-33.png",true)
        )
    }

    fun onItemClicked(product: ItemBeer) {
        _navigation.value = Event(product)
    }
}