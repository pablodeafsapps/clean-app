package es.plexus.android.plexuschuck.domainlayer.feature.login

import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.base.FailureBo
import es.plexus.android.plexuschuck.domainlayer.usecase.LOGIN_UC_TAG
import es.plexus.android.plexuschuck.domainlayer.usecase.REGISTER_UC_TAG
import kotlinx.coroutines.CoroutineScope
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

internal class LoginDomainLayerBridgeImpl : LoginDomainLayerBridge<List<String?>, Boolean>, KoinComponent {

    private val loginUserApiUc: DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>? by inject(name = LOGIN_UC_TAG)
    private val registerUserApiUc: DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>? by inject(name = REGISTER_UC_TAG)

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