package es.plexus.android.plexuschuck.domainlayer.feature.login

import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge

const val LOGIN_DOMAIN_TAG = "loginDomainLayerBridge"

interface LoginDomainLayerBridge : BaseDomainLayerBridge {

    fun request()

}