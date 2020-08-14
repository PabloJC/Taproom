package com.pabji.taproom.ui.common.base

import androidx.lifecycle.ViewModel
import com.pabji.taproom.ui.common.Scope
import kotlinx.coroutines.CoroutineDispatcher

open class BaseViewModel(uiDispatcher: CoroutineDispatcher) : ViewModel(), Scope by Scope.Impl(
    uiDispatcher
) {

    init {
        this.initScope()
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }
}