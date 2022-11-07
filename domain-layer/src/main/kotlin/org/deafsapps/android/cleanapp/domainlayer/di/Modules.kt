package org.deafsapps.android.cleanapp.domainlayer.di

import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract.Datalayer.Companion.AUTHENTICATION_REPOSITORY_TAG
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract.Datalayer.Companion.DATA_REPOSITORY_TAG
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract.Datalayer.Companion.PERSISTENCE_REPOSITORY_TAG
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract.Datalayer.Companion.SESSION_REPOSITORY_TAG
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBoWrapper
import org.deafsapps.android.cleanapp.domainlayer.domain.UserLoginBo
import org.deafsapps.android.cleanapp.domainlayer.domain.UserSessionBo
import org.deafsapps.android.cleanapp.domainlayer.feature.login.LOGIN_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.cleanapp.domainlayer.feature.login.LoginDomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.feature.login.LoginDomainLayerBridgeImpl
import org.deafsapps.android.cleanapp.domainlayer.feature.main.MAIN_DOMAIN_BRIDGE_TAG
import org.deafsapps.android.cleanapp.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.feature.main.MainDomainLayerBridgeImpl
import org.deafsapps.android.cleanapp.domainlayer.usecase.*
import org.deafsapps.android.cleanapp.domainlayer.usecase.FetchSessionUserUc
import org.deafsapps.android.cleanapp.domainlayer.usecase.LoginUserUc
import org.deafsapps.android.cleanapp.domainlayer.usecase.LogoutUserUc
import org.deafsapps.android.cleanapp.domainlayer.usecase.RegisterUserUc
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * This variable represents the 'domain-layer' dependencies module to be used by Koin. It basically
 * includes bridge and use-case definitions.
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
val domainLayerModule = module(override = true) {
    // bridge
    factory<LoginDomainLayerBridge<UserLoginBo>>(named(name = LOGIN_DOMAIN_BRIDGE_TAG)) {
        LoginDomainLayerBridgeImpl(
                saveUsersUc = get(named(name = SAVE_USERS_UC_TAG)),
                fetchUsersUc = get(named(name = FETCH_USERS_UC_TAG)),
                loginUserUc = get(named(name = LOGIN_UC_TAG)),
                registerUserUc = get(named(name = REGISTER_UC_TAG)),
                fetchSessionUser = get(named(name = FETCH_USER_SESSION_UC_TAG))
        )
    }
    factory<MainDomainLayerBridge<JokeBoWrapper>>(named(name = MAIN_DOMAIN_BRIDGE_TAG)) {
        MainDomainLayerBridgeImpl(
            fetchJokesUc = get(named(name = FETCH_JOKES_UC_TAG)),
            logoutUserUc = get(named(name = LOGOUT_UC_TAG)))
    }
    // use-case
    factory<DomainlayerContract.Presentationlayer.UseCase<UserLoginBo, Boolean>>(named(name = LOGIN_UC_TAG)) {
        LoginUserUc(authenticationRepository = get(named(name = AUTHENTICATION_REPOSITORY_TAG)))
    }
    factory<DomainlayerContract.Presentationlayer.UseCase<Any, Boolean>>(named(name = LOGOUT_UC_TAG)) {
        LogoutUserUc(authenticationRepository = get(named(name = AUTHENTICATION_REPOSITORY_TAG)))
    }
    factory<DomainlayerContract.Presentationlayer.UseCase<UserLoginBo, Boolean>>(named(name = REGISTER_UC_TAG)) {
        RegisterUserUc(authenticationRepository = get(named(name = AUTHENTICATION_REPOSITORY_TAG)))
    }
    factory<DomainlayerContract.Presentationlayer.UseCase<Nothing, JokeBoWrapper>>(named(name = FETCH_JOKES_UC_TAG)) {
        FetchJokesUc(dataRepository = get(named(name = DATA_REPOSITORY_TAG)))
    }
    factory<DomainlayerContract.Presentationlayer.UseCase<Any, UserSessionBo>>(named(name = FETCH_USER_SESSION_UC_TAG)) {
        FetchSessionUserUc(sessionRepository = get(named(name = SESSION_REPOSITORY_TAG)))
    }
    factory<DomainlayerContract.Presentationlayer.UseCase<Any, Boolean>>(named(name = SAVE_USERS_UC_TAG)) {
        SaveUsersUc(persistenceRepository = get(named(name = PERSISTENCE_REPOSITORY_TAG)))
    }
    factory<DomainlayerContract.Presentationlayer.UseCase<Any, String>>(named(name = FETCH_USERS_UC_TAG)) {
        FetchUsersUc(persistenceRepository = get(named(name = PERSISTENCE_REPOSITORY_TAG)))
    }
}
