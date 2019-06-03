package org.deafsapps.android.cleanapp.domainlayer.di

import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBo
import org.deafsapps.android.cleanapp.domainlayer.feature.login.LoginDomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.feature.login.LoginDomainLayerBridgeImpl
import org.deafsapps.android.cleanapp.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.feature.main.MainDomainLayerBridgeImpl
import org.deafsapps.android.cleanapp.domainlayer.usecase.FetchJokesApiUc
import org.deafsapps.android.cleanapp.domainlayer.usecase.LoginUserApiUc
import org.deafsapps.android.cleanapp.domainlayer.usecase.RegisterUserApiUc
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainLayerModule = module(override = true) {
    factory<LoginDomainLayerBridge<List<String?>, Boolean>>(named("loginDomainLayerBridge")) { LoginDomainLayerBridgeImpl() }
    factory<MainDomainLayerBridge<List<String>?, List<JokeBo>>>(named("mainDomainLayerBridge")) { MainDomainLayerBridgeImpl() }

    factory<DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>>(named("loginUserApiUc")) { LoginUserApiUc() }
    factory<DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>>(named("registerUserApiUc")) { RegisterUserApiUc() }
    factory<DomainlayerContract.Presentationlayer.UseCase<List<String>?, List<JokeBo>>>(named("fetchJokesApiUc")) { FetchJokesApiUc() }
}