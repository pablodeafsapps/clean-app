package es.plexus.android.plexuschuck.domainlayer.feature.splash

import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge

const val SPLASH_DOMAIN_BRIDGE_TAG = "splashDomainLayerBridge"

interface SplashDomainLayerBridge : BaseDomainLayerBridge {

    fun request()

}

internal class SplashDomainLayerBridgeImpl : SplashDomainLayerBridge {

    override fun request() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}