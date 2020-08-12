package com.pabji.taproom

import android.app.Application
import com.pabji.taproom.di.initDI

class TaproomApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}