package org.deafsapps.android.cleanapp.datalayer.di

import org.deafsapps.android.cleanapp.datalayer.DataLayerContract
import org.deafsapps.android.cleanapp.datalayer.datasource.FirebaseDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val dataLayerModule = module(override = true) {
    single("context") { androidContext() }

    factory<DataLayerContract.DataSource>("firebaseDataSource") { FirebaseDataSource() }
}