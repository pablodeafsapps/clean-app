package es.plexus.android.plexuschuck.domainlayer.di

import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridge
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridgeImpl
import org.koin.dsl.module.module

val domainLayerModule = module(override = true) {

    factory<LoginDomainLayerBridge<List<String?>, Boolean>>("loginDomainLayerBridge") { LoginDomainLayerBridgeImpl() }

//    factory<DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>>("loginUserApiUc") { LoginUserApiUc() }
//    factory<DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>>("registerUserApiUc") { RegisterUserApiUc() }
}