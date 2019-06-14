package es.plexus.android.plexuschuck.domainlayer.feature.login

import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.base.FailureBo
import kotlinx.coroutines.CoroutineScope
import org.koin.standalone.KoinComponent

class LoginDomainLayerBridgeImpl : LoginDomainLayerBridge<List<String?>, Boolean>, KoinComponent {

    override fun loginUser(scope: CoroutineScope, params: List<String?>, onResult: (Either<FailureBo, Boolean>) -> Unit) {

    }

    override fun registerUser(scope: CoroutineScope, params: List<String?>, onResult: (Either<FailureBo, Boolean>) -> Unit) {

    }

}