package es.plexus.android.plexuschuck.domainlayer.feature.detail

import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge

const val DETAIL_DOMAIN_BRIDGE_TAG = "detailDomainLayerBridge"

interface DetailDomainLayerBridge : BaseDomainLayerBridge {

    fun request()

}

internal class DetailDomainLayerBridgeImpl : DetailDomainLayerBridge {

    override fun request() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}