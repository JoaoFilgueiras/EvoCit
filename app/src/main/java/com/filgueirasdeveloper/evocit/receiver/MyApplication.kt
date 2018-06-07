package com.filgueirasdeveloper.evocit.receiver

import android.app.Application

class MyApplication: Application() {
    companion object {
        @get:Synchronized
        lateinit var instance: MyApplication
    }

    fun setConnectionListener(listener: MapaReceiver.ConnectionReceiverListener){
        MapaReceiver.connectionReceiverListener = listener
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}