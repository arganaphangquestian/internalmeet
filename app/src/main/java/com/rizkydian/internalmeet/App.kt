package com.rizkydian.internalmeet

import android.app.Application

// TODO Add hilt
@Suppress("unused")
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }
}