package es.plexus.android.plexuschuck.domainlayer.feature.login

import es.plexus.android.plexuschuck.domainlayer.base.BaseDomainLayerBridge
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import kotlinx.coroutines.CoroutineScope

const val LOGIN_DOMAIN_TAG = "loginDomainLayerBridge"

interface LoginDomainLayerBridge<in T, out S> : BaseDomainLayerBridge {

    fun loginUser(scope: CoroutineScope, params: T, onResult: (Either<FailureBo, S>) -> Unit = {})
    fun registerUser(scope: CoroutineScope, params: T, onResult: (Either<FailureBo, S>) -> Unit = {})

}