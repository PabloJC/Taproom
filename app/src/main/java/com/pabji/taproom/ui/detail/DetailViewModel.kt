package com.pabji.taproom.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.domain.model.Beer
import com.pabji.taproom.ui.common.BaseViewModel
import com.pabji.usecases.GetBeerDetail
import com.pabji.usecases.SetEmptyBarrel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(
    private val id: Long,
    private val getBeer: GetBeerDetail,
    private val setEmptyBarrel: SetEmptyBarrel,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val _model = MutableLiveData<Beer>()
    val model: LiveData<Beer>
        get() {
            if (_model.value == null) loadData()
            return _model
        }

    fun loadData() {
        launch {
            getBeer(id).collect { _model.value = it }
        }
    }

    fun onEmptyBarrelButtonClick() {
        launch {
            setEmptyBarrel(id, !isBarrelEmpty())
        }
    }

    fun isBarrelEmpty() = _model.value?.isBarrelEmpty ?: false
}