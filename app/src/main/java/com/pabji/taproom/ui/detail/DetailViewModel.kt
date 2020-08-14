package com.pabji.taproom.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.taproom.data.uimodel.UIBeerDetail
import com.pabji.taproom.data.uimodel.toUIModel
import com.pabji.taproom.ui.common.base.BaseViewModel
import com.pabji.usecases.GetBeerDetail
import com.pabji.usecases.SetEmptyBarrel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailViewModel(
    private val id: Long,
    private val getBeer: GetBeerDetail,
    private val setEmptyBarrel: SetEmptyBarrel,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UIBeerDetail>()
    val model: LiveData<UIBeerDetail>
        get() {
            if (_model.value == null) loadData()
            return _model
        }

    fun onEmptyBarrelButtonClick() {
        launch {
            setEmptyBarrel(id, !isBarrelEmpty())
        }
    }

    private fun loadData() {
        launch {
            getBeer(id).map { it.toUIModel() }.collect { _model.value = it }
        }
    }

    private fun isBarrelEmpty() = _model.value?.isNotAvailable ?: false
}