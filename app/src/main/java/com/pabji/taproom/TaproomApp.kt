package com.pabji.taproom

import android.app.Application

class TaproomApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}