package com.mukiva.p2pmessanger

import android.app.Application
import com.mukiva.p2pmessanger.di.initKoin
import org.koin.android.ext.koin.androidContext

class P2PApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@P2PApp)
        }
    }

}