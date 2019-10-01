package es.plexus.android.plexuschuck.datalayer.di

import es.plexus.android.plexuschuck.datalayer.DataLayerContract
import es.plexus.android.plexuschuck.datalayer.DataLayerContract.Companion.AUTHENTICATION_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.DataLayerContract.Companion.JOKES_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.datasource.FirebaseDataSourceImpl
import es.plexus.android.plexuschuck.datalayer.datasource.IcndbDataSourceImpl
import es.plexus.android.plexuschuck.datalayer.repository.Repository
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.AUTHENTICATION_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.DATA_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * This variable represents the 'data-layer' dependencies module to be used by Koin. It basically includes repository
 * and data-source definitions.
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
val dataLayerModule = module(override = true) {
    factory<DataLayerContract.AuthenticationDataSource>(named(name = AUTHENTICATION_DATA_SOURCE_TAG)) { FirebaseDataSourceImpl() }
    factory<DataLayerContract.JokesDataSource>(named(name = JOKES_DATA_SOURCE_TAG)) { IcndbDataSourceImpl() }

    single<DomainlayerContract.Datalayer.AuthenticationRepository<List<String>, Boolean>>(named(name = AUTHENTICATION_REPOSITORY_TAG)) { Repository }
    single<DomainlayerContract.Datalayer.DataRepository<List<String>?, List<JokeBo>>>(named(name = DATA_REPOSITORY_TAG)) { Repository }
}