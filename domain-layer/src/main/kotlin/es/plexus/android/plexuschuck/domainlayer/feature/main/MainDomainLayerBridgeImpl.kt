package es.plexus.android.plexuschuck.domainlayer.feature.main

import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo
import es.plexus.android.plexuschuck.domainlayer.usecase.MAIN_UC_TAG
import kotlinx.coroutines.CoroutineScope
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

internal class MainDomainLayerBridgeImpl : MainDomainLayerBridge<List<String>?, List<JokeBo>>, KoinComponent {

    private val fetchJokesApiUc: DomainlayerContract.Presentationlayer.UseCase<List<String>?, List<JokeBo>> by inject(
        name = MAIN_UC_TAG
    )

    override fun fetchJokes(
        scope: CoroutineScope,
        params: List<String>?,
        onResult: (Either<FailureBo, List<JokeBo>>) -> Unit
    ) {
        fetchJokesApiUc.invoke(scope = scope, params = params, onResult = onResult)
    }

}