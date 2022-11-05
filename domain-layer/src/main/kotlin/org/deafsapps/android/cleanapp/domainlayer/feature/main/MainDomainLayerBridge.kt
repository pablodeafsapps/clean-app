package org.deafsapps.android.cleanapp.domainlayer.feature.main

import arrow.core.Either
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.BaseDomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.domain.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBoWrapper
import kotlinx.coroutines.CoroutineScope

const val MAIN_DOMAIN_BRIDGE_TAG = "mainDomainLayerBridge"

/**
 * An entity which gathers all the available functionality related to the 'main' feature
 */
interface MainDomainLayerBridge<out S> : BaseDomainLayerBridge {

    /**
     * A function which allows to query and handle joke data
     *
     * @param [scope] The [CoroutineScope] associated, which can be used to handle an enclosing Kotlin Coroutine
     * @param [onResult] A callback to send back any data of interest
     */
    fun fetchJokes(scope: CoroutineScope, onResult: (Either<FailureBo, S>) -> Unit = {})

    /**
     * A function which allows to logout an user
     *
     * @param [scope] The [CoroutineScope] associated, which can be used to handle an enclosing Kotlin Coroutine
     * @param [onResult] A callback to send back any data of interest
     */
    fun logout(scope: CoroutineScope, onResult: (Either<FailureBo, Boolean>) -> Unit = {})

}

internal class MainDomainLayerBridgeImpl(
    private val fetchJokesUc: DomainlayerContract.Presentationlayer.UseCase<Any, JokeBoWrapper>,
    private val logoutUserUc: DomainlayerContract.Presentationlayer.UseCase<Any, Boolean>
) : MainDomainLayerBridge<JokeBoWrapper> {

    override fun fetchJokes(
        scope: CoroutineScope,
        onResult: (Either<FailureBo, JokeBoWrapper>) -> Unit
    ) {
        fetchJokesUc.invoke(scope = scope, onResult = onResult)
    }

    override fun logout(
        scope: CoroutineScope,
        onResult: (Either<FailureBo, Boolean>) -> Unit
    ) {
        logoutUserUc.invoke(scope = scope, onResult = onResult)
    }

}
