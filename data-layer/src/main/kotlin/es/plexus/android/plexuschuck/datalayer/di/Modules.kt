package es.plexus.android.plexuschuck.datalayer.di

import es.plexus.android.plexuschuck.datalayer.DataLayerContract
import es.plexus.android.plexuschuck.datalayer.DataLayerContract.Companion.FIREBASE_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.DataLayerContract.Companion.ICNDB_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.datasource.FirebaseDataSourceImpl
import es.plexus.android.plexuschuck.datalayer.datasource.IcndbDataSourceImpl
import es.plexus.android.plexuschuck.datalayer.repository.Repository
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.FIREBASE_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.ICNDB_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo
import org.koin.dsl.module.module

/**
 * This variable represents the 'data-layer' dependencies module to be used by Koin. It basically includes repository
 * and data-source definitions.
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
val dataLayerModule = module(override = true) {
    factory<DataLayerContract.FirebaseDataSource>(name = FIREBASE_DATA_SOURCE_TAG) { FirebaseDataSourceImpl() }
    factory<DataLayerContract.IcndbDataSource>(name = ICNDB_DATA_SOURCE_TAG) { IcndbDataSourceImpl() }

    single<DomainlayerContract.Datalayer.FirebaseRepository<List<String>, Boolean>>(name = FIREBASE_REPOSITORY_TAG) { Repository }
    single<DomainlayerContract.Datalayer.IcndbRepository<List<String>?, List<JokeBo>>>(name = ICNDB_REPOSITORY_TAG) { Repository }
}