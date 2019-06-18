package es.plexus.android.plexuschuck.domainlayer.di

import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.feature.login.LOGIN_DOMAIN_TAG
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridge
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridgeImpl
import es.plexus.android.plexuschuck.domainlayer.usecase.LOGIN_UC_TAG
import es.plexus.android.plexuschuck.domainlayer.usecase.LoginUserApiUc
import es.plexus.android.plexuschuck.domainlayer.usecase.REGISTER_UC_TAG
import es.plexus.android.plexuschuck.domainlayer.usecase.RegisterUserApiUc
import org.koin.dsl.module.module

const val FIREBASE_REPOSITORY_TAG = "firebaseRepository"
const val FIREBASE_DATA_SOURCE_TAG = "firebaseDataSource"

val domainLayerModule = module(override = true) {

    factory<LoginDomainLayerBridge<List<String?>, Boolean>>(name = LOGIN_DOMAIN_TAG) { LoginDomainLayerBridgeImpl() }

    factory<DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>>(name = LOGIN_UC_TAG) { LoginUserApiUc() }
    factory<DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>>(name = REGISTER_UC_TAG) { RegisterUserApiUc() }
}