package org.deafsapps.android.cleanapp.domainlayer.di

import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.DomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.feature.LoginDomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.feature.LoginDomainLayerBridgeImpl
import org.deafsapps.android.cleanapp.domainlayer.usecase.LoginUserApiUc
import org.deafsapps.android.cleanapp.domainlayer.usecase.RegisterUserApiUc
import org.koin.dsl.module.module

val domainLayerModule = module(override = true) {
    factory<LoginDomainLayerBridge<String?>>("loginDomainLayerBridge") { LoginDomainLayerBridgeImpl() }

    factory<DomainlayerContract.Presentationlayer.UseCase<String?>>("loginUserApiUc") { LoginUserApiUc() }
    factory<DomainlayerContract.Presentationlayer.UseCase<String?>>("registerUserApiUc") { RegisterUserApiUc() }
}