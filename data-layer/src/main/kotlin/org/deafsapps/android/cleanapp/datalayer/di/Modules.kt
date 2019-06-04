package org.deafsapps.android.cleanapp.datalayer.di

import org.deafsapps.android.cleanapp.datalayer.DataLayerContract
import org.deafsapps.android.cleanapp.datalayer.datasource.FirebaseDataSourceImpl
import org.deafsapps.android.cleanapp.datalayer.datasource.IcndbDataSourceImpl
import org.deafsapps.android.cleanapp.datalayer.repository.Repository
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBo
import org.koin.dsl.module.module

val dataLayerModule = module(override = true) {
    factory<DataLayerContract.FirebaseDataSource>("firebaseDataSource") { FirebaseDataSourceImpl() }
    factory<DataLayerContract.IcndbDataSource>("icndbDataSource") { IcndbDataSourceImpl() }

    single<DomainlayerContract.Datalayer.FirebaseRepository<List<String>, Boolean>>("firebaseRepository") { Repository }
    single<DomainlayerContract.Datalayer.IcndbRepository<List<String>?, List<JokeBo>>>("icndbRepository") { Repository }
}