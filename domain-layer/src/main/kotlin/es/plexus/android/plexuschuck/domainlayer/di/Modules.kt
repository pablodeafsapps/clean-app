package es.plexus.android.plexuschuck.domainlayer.di

import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo
import es.plexus.android.plexuschuck.domainlayer.feature.login.LOGIN_DOMAIN_TAG
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridge
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridgeImpl
import es.plexus.android.plexuschuck.domainlayer.feature.main.MAIN_DOMAIN_TAG
import es.plexus.android.plexuschuck.domainlayer.feature.main.MainDomainLayerBridge
import es.plexus.android.plexuschuck.domainlayer.feature.main.MainDomainLayerBridgeImpl
import es.plexus.android.plexuschuck.domainlayer.usecase.*
import org.koin.dsl.module.module

val domainLayerModule = module(override = true) {
    factory<LoginDomainLayerBridge<List<String?>, Boolean>>(name = LOGIN_DOMAIN_TAG) { LoginDomainLayerBridgeImpl() }
    factory<MainDomainLayerBridge<List<String>?, List<JokeBo>>>(name = MAIN_DOMAIN_TAG) { MainDomainLayerBridgeImpl() }

    factory<DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>>(name = LOGIN_UC_TAG) { LoginUserApiUc() }
    factory<DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>>(name = REGISTER_UC_TAG) { RegisterUserApiUc() }
    factory<DomainlayerContract.Presentationlayer.UseCase<List<String>?, List<JokeBo>>>(name = MAIN_UC_TAG) { FetchJokesApiUc() }
}