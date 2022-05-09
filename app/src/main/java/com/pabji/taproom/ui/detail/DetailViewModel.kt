package com.pabji.taproom.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val getBeerDetail: GetBeerDetail,
    private val setEmptyBarrel: SetEmptyBarrel
) : ViewModel(){

    private val _model = MutableLiveData<UIBeerDetail>()
    val model: LiveData<UIBeerDetail>
        get() {
            if (_model.value == null) loadData()
            return _model
        }

    fun onEmptyBarrelButtonClick() {
        viewModelScope.launch {
            setEmptyBarrel(id, !isBarrelEmpty())
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            getBeerDetail(id).map { it.toUIModel() }.collect { _model.value = it }
        }
    }

    private fun isBarrelEmpty() = _model.value?.isNotAvailable ?: false
}