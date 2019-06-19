package es.plexus.android.plexuschuck.application.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import es.plexus.android.plexuschuck.datalayer.di.dataLayerModule
import es.plexus.android.plexuschuck.domainlayer.di.domainLayerModule
import es.plexus.android.plexuschuck.presentationlayer.di.presentationLayerModule
import org.koin.android.ext.android.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(presentationLayerModule, domainLayerModule, dataLayerModule))
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}