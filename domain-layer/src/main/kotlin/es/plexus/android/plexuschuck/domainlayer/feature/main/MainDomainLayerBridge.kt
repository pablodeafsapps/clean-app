package es.plexus.android.plexuschuck.domainlayer.feature.main

import arrow.core.Either
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBoWrapper
import kotlinx.coroutines.CoroutineScope

const val MAIN_DOMAIN_BRIDGE_TAG = "mainDomainLayerBridge"

/**
 *
 */
interface MainDomainLayerBridge<out S> : BaseDomainLayerBridge {

    /**
     *
     */
    fun fetchJokes(scope: CoroutineScope, onResult: (Either<FailureBo, S>) -> Unit = {})

}

internal class MainDomainLayerBridgeImpl(
    private val fetchJokesUc: DomainlayerContract.Presentationlayer.UseCase<Any, JokeBoWrapper>
) : MainDomainLayerBridge<JokeBoWrapper> {

    override fun fetchJokes(
        scope: CoroutineScope,
        onResult: (Either<FailureBo, JokeBoWrapper>) -> Unit
    ) {
        fetchJokesUc.invoke(scope = scope, onResult = onResult)
    }

}
