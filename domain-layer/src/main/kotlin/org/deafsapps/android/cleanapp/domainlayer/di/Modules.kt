package org.deafsapps.android.cleanapp.domainlayer.di

import org.deafsapps.android.cleanapp.domainlayer.DomainLayerContract
import org.deafsapps.android.cleanapp.domainlayer.feature.LoginDomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.feature.LoginDomainLayerBridgeImpl
import org.deafsapps.android.cleanapp.domainlayer.repository.Repository
import org.deafsapps.android.cleanapp.domainlayer.usecase.LoginUserApiUc
import org.deafsapps.android.cleanapp.domainlayer.usecase.RegisterUserApiUc
import org.koin.dsl.module.module

val domainLayerModule = module(override = true) {
    factory<LoginDomainLayerBridge>("loginDomainLayerBridge") { LoginDomainLayerBridgeImpl() }

    factory<DomainLayerContract.UseCase>("loginUserApiUc") { LoginUserApiUc() }
    factory<DomainLayerContract.UseCase>("registerUserApiUc") { RegisterUserApiUc() }

    single<DomainLayerContract.Repository> { Repository() }
}