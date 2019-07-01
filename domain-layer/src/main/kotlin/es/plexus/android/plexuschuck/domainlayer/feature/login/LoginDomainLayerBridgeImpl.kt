package es.plexus.android.plexuschuck.domainlayer.feature.login

import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.usecase.LOGIN_UC_TAG
import es.plexus.android.plexuschuck.domainlayer.usecase.REGISTER_UC_TAG
import kotlinx.coroutines.CoroutineScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

internal class LoginDomainLayerBridgeImpl : LoginDomainLayerBridge<List<String?>, Boolean>,
    KoinComponent {

    private val loginUserApiUc: DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>?
            by inject(named(name = LOGIN_UC_TAG))
    private val registerUserApiUc: DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>?
            by inject(named(name = REGISTER_UC_TAG))

    override fun loginUser(
        scope: CoroutineScope,
        params: List<String?>,
        onResult: (Either<FailureBo, Boolean>) -> Unit
    ) {
        loginUserApiUc?.invoke(scope = scope, params = params, onResult = onResult)
    }

    override fun registerUser(
        scope: CoroutineScope,
        params: List<String?>,
        onResult: (Either<FailureBo, Boolean>) -> Unit
    ) {
        registerUserApiUc?.invoke(scope = scope, params = params, onResult = onResult)
    }

}