package es.plexus.android.plexuschuck.domainlayer.feature.main

import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import kotlinx.coroutines.CoroutineScope

const val MAIN_DOMAIN_BRIDGE_TAG = "mainDomainLayerBridge"

interface MainDomainLayerBridge<in T, out S> : BaseDomainLayerBridge {

    fun fetchJokes(scope: CoroutineScope, params: T, onResult: (Either<FailureBo, S>) -> Unit = {})

}