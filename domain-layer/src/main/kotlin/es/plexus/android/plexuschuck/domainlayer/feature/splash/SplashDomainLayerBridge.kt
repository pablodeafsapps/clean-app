package es.plexus.android.plexuschuck.domainlayer.feature.splash

import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge

const val SPLASH_DOMAIN_TAG = "splashDomainLayerBridge"

interface SplashDomainLayerBridge : BaseDomainLayerBridge {

    fun request()

}