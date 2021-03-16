package es.plexus.android.plexuschuck.datalayer.di

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import es.plexus.android.plexuschuck.datalayer.datasource.AndroidDataSource
import es.plexus.android.plexuschuck.datalayer.datasource.AuthenticationDataSource
import es.plexus.android.plexuschuck.datalayer.datasource.AuthenticationDataSource.Companion.AUTHENTICATION_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.datasource.AuthenticationDataSource.Companion.AUTHENTICATOR_TAG
import es.plexus.android.plexuschuck.datalayer.datasource.ConnectivityDataSource
import es.plexus.android.plexuschuck.datalayer.datasource.ConnectivityDataSource.Companion.CONNECTIVITY_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.datasource.FirebaseDataSource
import es.plexus.android.plexuschuck.datalayer.datasource.IcndbDataSource
import es.plexus.android.plexuschuck.datalayer.datasource.JokesDataSource
import es.plexus.android.plexuschuck.datalayer.datasource.JokesDataSource.Companion.ICNDB_BASE_URL
import es.plexus.android.plexuschuck.datalayer.datasource.JokesDataSource.Companion.JOKES_API_SERVICE_TAG
import es.plexus.android.plexuschuck.datalayer.datasource.JokesDataSource.Companion.JOKES_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.datasource.PersistenceDataSource
import es.plexus.android.plexuschuck.datalayer.datasource.PersistenceDataSource.Companion.PERSISTENCE_DATA_SOURCE_TAG
import es.plexus.android.plexuschuck.datalayer.db.APP_DB_TAG
import es.plexus.android.plexuschuck.datalayer.db.AppDatabase
import es.plexus.android.plexuschuck.datalayer.repository.Repository
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.AUTHENTICATION_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.DATA_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.PERSISTENCE_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBoWrapper
import es.plexus.android.plexuschuck.domainlayer.domain.UserLoginBo
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * This variable represents the 'data-layer' dependencies module to be used by Koin. It basically
 * includes repository and data-source definitions.
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
val dataLayerModule = module(override = true) {
    // repository
    single {
        Repository.apply {
            connectivityDataSource = get(named(name = CONNECTIVITY_DATA_SOURCE_TAG))
            authenticationDataSource = get(named(name = AUTHENTICATION_DATA_SOURCE_TAG))
            jokesDataSource = get(named(name = JOKES_DATA_SOURCE_TAG))
            persistenceDataSource = get(named(name = PERSISTENCE_DATA_SOURCE_TAG))
        }
    }
    single<DomainlayerContract.Datalayer.AuthenticationRepository<UserLoginBo, Boolean>>(named(name = AUTHENTICATION_REPOSITORY_TAG)) {
        get<Repository>()
    }
    single<DomainlayerContract.Datalayer.DataRepository<JokeBoWrapper>>(named(name = DATA_REPOSITORY_TAG)) {
        get<Repository>()
    }
    single<DomainlayerContract.Datalayer.PersistencyRepository<String>>(named(name = PERSISTENCE_REPOSITORY_TAG)) {
        get<Repository>()
    }
    // data-source
    factory<ConnectivityDataSource>(named(name = CONNECTIVITY_DATA_SOURCE_TAG)) {
        AndroidDataSource(
            context = androidContext(),
            database = get(named(name = APP_DB_TAG))
        )
    }
    factory<AuthenticationDataSource>(named(name = AUTHENTICATION_DATA_SOURCE_TAG)) {
        FirebaseDataSource(fbAuth = get(named(name = AUTHENTICATOR_TAG)))
    }
    factory<JokesDataSource>(named(name = JOKES_DATA_SOURCE_TAG)) {
        IcndbDataSource(get(named(name = JOKES_API_SERVICE_TAG)))
    }
    factory<PersistenceDataSource>(named(name = PERSISTENCE_DATA_SOURCE_TAG)) {
        AndroidDataSource(
            context = androidContext(),
            database = get(named(name = APP_DB_TAG))
        )
    }
    // retrofit
    single<Retrofit>(named(name = JOKES_API_SERVICE_TAG)) {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(ICNDB_BASE_URL)
            .build()
    }
    // firebase
    single<FirebaseAuth>(named(name = AUTHENTICATOR_TAG)) {
        FirebaseAuth.getInstance()
    }
    // db
    single<AppDatabase>(named(name = APP_DB_TAG)) {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "db-chuck"
        ).build()
    }
}
