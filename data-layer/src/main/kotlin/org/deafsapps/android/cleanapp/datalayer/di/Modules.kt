package org.deafsapps.android.cleanapp.datalayer.di

import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.deafsapps.android.cleanapp.datalayer.datasource.AndroidDataSource
import org.deafsapps.android.cleanapp.datalayer.datasource.AuthenticationDataSource
import org.deafsapps.android.cleanapp.datalayer.datasource.AuthenticationDataSource.Companion.AUTHENTICATION_DATA_SOURCE_TAG
import org.deafsapps.android.cleanapp.datalayer.datasource.AuthenticationDataSource.Companion.AUTHENTICATOR_TAG
import org.deafsapps.android.cleanapp.datalayer.datasource.ConnectivityDataSource
import org.deafsapps.android.cleanapp.datalayer.datasource.ConnectivityDataSource.Companion.CONNECTIVITY_DATA_SOURCE_TAG
import org.deafsapps.android.cleanapp.datalayer.datasource.FirebaseDataSource
import org.deafsapps.android.cleanapp.datalayer.datasource.IcndbDataSource
import org.deafsapps.android.cleanapp.datalayer.datasource.JokesDataSource
import org.deafsapps.android.cleanapp.datalayer.datasource.JokesDataSource.Companion.ICNDB_BASE_URL
import org.deafsapps.android.cleanapp.datalayer.datasource.JokesDataSource.Companion.JOKES_API_SERVICE_TAG
import org.deafsapps.android.cleanapp.datalayer.datasource.JokesDataSource.Companion.JOKES_DATA_SOURCE_TAG
import org.deafsapps.android.cleanapp.datalayer.datasource.SessionDataSource
import org.deafsapps.android.cleanapp.datalayer.datasource.SessionDataSource.Companion.SESSION_DATA_SOURCE_TAG
import org.deafsapps.android.cleanapp.datalayer.repository.Repository
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract.Datalayer.Companion.AUTHENTICATION_REPOSITORY_TAG
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract.Datalayer.Companion.DATA_REPOSITORY_TAG
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract.Datalayer.Companion.SESSION_REPOSITORY_TAG
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBoWrapper
import org.deafsapps.android.cleanapp.domainlayer.domain.UserLoginBo
import org.deafsapps.android.cleanapp.domainlayer.domain.UserSessionBo
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
            sessionDataSource = get(named(SESSION_DATA_SOURCE_TAG))
        }
    }
    single<DomainlayerContract.Datalayer.AuthenticationRepository<UserLoginBo, Boolean>>(
        named(name = AUTHENTICATION_REPOSITORY_TAG)
    ) {
        get<Repository>()
    }
    single<DomainlayerContract.Datalayer.DataRepository<JokeBoWrapper>>(named(name = DATA_REPOSITORY_TAG)) {
        get<Repository>()
    }
    single<DomainlayerContract.Datalayer.SessionRepository<UserSessionBo>>(
        named(name = SESSION_REPOSITORY_TAG)
    ) {
        get<Repository>()
    }
    // data-source
    factory<ConnectivityDataSource>(named(name = CONNECTIVITY_DATA_SOURCE_TAG)) {
        AndroidDataSource(context = androidContext())
    }
    factory<AuthenticationDataSource>(named(name = AUTHENTICATION_DATA_SOURCE_TAG)) {
        FirebaseDataSource(fbAuth = get(named(name = AUTHENTICATOR_TAG)))
    }
    factory<SessionDataSource>(named(name = SESSION_DATA_SOURCE_TAG)) {
        AndroidDataSource(context = androidContext())
    }
    factory<JokesDataSource>(named(name = JOKES_DATA_SOURCE_TAG)) {
        IcndbDataSource(get(named(name = JOKES_API_SERVICE_TAG)))
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
}
