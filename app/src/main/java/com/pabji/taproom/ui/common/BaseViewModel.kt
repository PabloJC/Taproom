package com.pabji.taproom.ui.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher

open class BaseViewModel(uiDispatcher: CoroutineDispatcher) : ViewModel(), Scope by Scope.Impl(uiDispatcher) {

    init {
        this.initScope()
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }
}