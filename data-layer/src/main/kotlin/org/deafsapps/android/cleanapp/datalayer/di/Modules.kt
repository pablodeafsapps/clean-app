package org.deafsapps.android.cleanapp.datalayer.di

import org.deafsapps.android.cleanapp.datalayer.DataLayerContract
import org.deafsapps.android.cleanapp.datalayer.datasource.FirebaseDataSource
import org.deafsapps.android.cleanapp.datalayer.repository.Repository
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.koin.dsl.module.module

val dataLayerModule = module(override = true) {
    factory<DataLayerContract.DataSource>("firebaseDataSource") { FirebaseDataSource() }

    single<DomainlayerContract.Datalayer.Repository<String>> { Repository }
}