package org.deafsapps.android.cleanapp.domainlayer.feature.main

import kotlinx.coroutines.CoroutineScope
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBo
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class MainDomainLayerBridgeImpl : MainDomainLayerBridge<List<String>?, List<JokeBo>>, KoinComponent {

    private val fetchJokesApiUc: DomainlayerContract.Presentationlayer.UseCase<List<String>?, List<JokeBo>> by inject("fetchJokesApiUc")

    override fun fetchJokes(
        scope: CoroutineScope,
        params: List<String>?,
        onResult: (Either<FailureBo, List<JokeBo>>) -> Unit
    ) {
        fetchJokesApiUc.invoke(scope = scope, params = params, onResult = onResult)
    }

}