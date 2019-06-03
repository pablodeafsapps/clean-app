package org.deafsapps.android.cleanapp.application.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import org.deafsapps.android.cleanapp.datalayer.di.dataLayerModule
import org.deafsapps.android.cleanapp.domainlayer.di.domainLayerModule
import org.deafsapps.android.cleanapp.presentationlayer.di.presentationLayerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(listOf(presentationLayerModule, domainLayerModule, dataLayerModule))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}