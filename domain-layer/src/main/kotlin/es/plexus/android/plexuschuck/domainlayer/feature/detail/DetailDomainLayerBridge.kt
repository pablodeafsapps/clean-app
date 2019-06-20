package es.plexus.android.plexuschuck.domainlayer.feature.detail

import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge

const val DETAIL_DOMAIN_BRIDGE_TAG = "detailDomainLayerBridge"

interface DetailDomainLayerBridge : BaseDomainLayerBridge {

    fun request()

}