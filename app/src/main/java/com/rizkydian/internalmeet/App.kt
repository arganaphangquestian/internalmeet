package com.rizkydian.internalmeet

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }

    companion object {
        lateinit var instance: App
            private set
    }
}