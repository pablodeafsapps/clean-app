package org.deafsapps.android.cleanapp.domainlayer.feature

import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class LoginDomainLayerBridgeImpl : LoginDomainLayerBridge<String?>, KoinComponent {

    private val loginUserApiUc: DomainlayerContract.Presentationlayer.UseCase<String?> by inject("loginUserApiUc")
    private val registerUserApiUc: DomainlayerContract.Presentationlayer.UseCase<String?> by inject("registerUserApiUc")

    override fun loginUser(params: List<String?>, onResult: (Either<FailureBo, Boolean>) -> Unit) {
        loginUserApiUc.invoke(params = params, onResult = onResult)
    }

    override fun registerUser(params: List<String?>, onResult: (Either<FailureBo, Boolean>) -> Unit) {
        registerUserApiUc.invoke(params = params, onResult = onResult)
    }

}