package es.plexus.android.plexuschuck.datalayer.di

import es.plexus.android.plexuschuck.datalayer.DataLayerContract
import es.plexus.android.plexuschuck.datalayer.datasource.FirebaseDataSourceImpl
import es.plexus.android.plexuschuck.datalayer.repository.Repository
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.di.FIREBASE_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.domainlayer.di.FIREBASE_REPOSITORY_TAG
import org.koin.dsl.module.module

val dataLayerModule = module(override = true) {
    factory<DataLayerContract.FirebaseDataSource>(name = FIREBASE_DATA_SOURCE_TAG) { FirebaseDataSourceImpl() }

    single<DomainlayerContract.Datalayer.FirebaseRepository<List<String>, Boolean>>(name = FIREBASE_REPOSITORY_TAG) { Repository }
}