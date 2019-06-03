package org.deafsapps.android.cleanapp.domainlayer.feature.login

import kotlinx.coroutines.CoroutineScope
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class LoginDomainLayerBridgeImpl : LoginDomainLayerBridge<List<String?>, Boolean>, KoinComponent {

    private val loginUserApiUc: DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean> by inject(named("loginUserApiUc"))
    private val registerUserApiUc: DomainlayerContract.Presentationlayer.UseCase<List<String?>, Boolean>
            by inject(named("registerUserApiUc"))

    override fun loginUser(
        scope: CoroutineScope,
        params: List<String?>,
        onResult: (Either<FailureBo, Boolean>) -> Unit
    ) {
        loginUserApiUc.invoke(scope = scope, params = params, onResult = onResult)
    }

    override fun registerUser(
        scope: CoroutineScope,
        params: List<String?>,
        onResult: (Either<FailureBo, Boolean>) -> Unit
    ) {
        registerUserApiUc.invoke(scope = scope, params = params, onResult = onResult)
    }

}