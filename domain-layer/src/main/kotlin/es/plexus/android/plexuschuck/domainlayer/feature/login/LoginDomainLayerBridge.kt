package es.plexus.android.plexuschuck.domainlayer.feature.login

import arrow.core.Either
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.UserLoginBo
import kotlinx.coroutines.CoroutineScope

const val LOGIN_DOMAIN_BRIDGE_TAG = "loginDomainLayerBridge"

interface LoginDomainLayerBridge<in T, out S> : BaseDomainLayerBridge {

    fun loginUser(scope: CoroutineScope, params: T, onResult: (Either<FailureBo, S>) -> Unit = {})
    fun registerUser(scope: CoroutineScope, params: T, onResult: (Either<FailureBo, S>) -> Unit = {})

}

internal class LoginDomainLayerBridgeImpl(
    private val loginUserUc: DomainlayerContract.Presentationlayer.UseCase<UserLoginBo, Boolean>,
    private val registerUserUc: DomainlayerContract.Presentationlayer.UseCase<UserLoginBo, Boolean>
) : LoginDomainLayerBridge<UserLoginBo, Boolean> {

    override fun loginUser(
        scope: CoroutineScope, params: UserLoginBo, onResult: (Either<FailureBo, Boolean>) -> Unit
    ) {
        loginUserUc.invoke(scope = scope, params = params, onResult = onResult)
    }

    override fun registerUser(
        scope: CoroutineScope, params: UserLoginBo, onResult: (Either<FailureBo, Boolean>) -> Unit
    ) {
        registerUserUc.invoke(scope = scope, params = params, onResult = onResult)
    }

}