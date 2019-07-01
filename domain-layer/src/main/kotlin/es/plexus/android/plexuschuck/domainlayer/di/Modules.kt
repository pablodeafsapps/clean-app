package es.plexus.android.plexuschuck.domainlayer.di

import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo
import es.plexus.android.plexuschuck.domainlayer.feature.login.LOGIN_DOMAIN_BRIDGE_TAG
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridge
import es.plexus.android.plexuschuck.domainlayer.feature.login.LoginDomainLayerBridgeImpl
import es.plexus.android.plexuschuck.domainlayer.feature.main.MAIN_DOMAIN_BRIDGE_TAG
import es.plexus.android.plexuschuck.domainlayer.feature.main.MainDomainLayerBridge
import es.plexus.android.plexuschuck.domainlayer.feature.main.MainDomainLayerBridgeImpl
import es.plexus.android.plexuschuck.domainlayer.usecase.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * This variable represents the 'domain-layer' dependencies module to be used by Koin. It basically includes bridge and
 * use-case definitions.
 *
 * @author Pablo L. Sordo
 * @since 1.0
 */
val domainLayerModule = module(override = true) {
    factory<LoginDomainLayerBridge<List<String?>, Boolean>>(named(name = LOGIN_DOMAIN_BRIDGE_TAG)) { LoginDomainLayerBridgeImpl() }
    factory<MainDomainLayerBridge<List<String>?, List<JokeBo>>>(named(name = MAIN_DOMAIN_BRIDGE_TAG)) { MainDomainLayerBridgeImpl() }

    factory<DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>>(named(name = LOGIN_UC_TAG)) { LoginUserApiUc() }
    factory<DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>>(named(name = REGISTER_UC_TAG)) { RegisterUserApiUc() }
    factory<DomainlayerContract.Presentationlayer.UseCase<List<String>?, List<JokeBo>>>(named(name = MAIN_UC_TAG)) { FetchJokesApiUc() }
}