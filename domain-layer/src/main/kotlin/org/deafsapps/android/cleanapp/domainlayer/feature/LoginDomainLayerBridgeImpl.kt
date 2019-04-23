package org.deafsapps.android.cleanapp.domainlayer.feature

import org.deafsapps.android.cleanapp.datalayer.base.Failure
import org.deafsapps.android.cleanapp.domainlayer.DomainLayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class LoginDomainLayerBridgeImpl : LoginDomainLayerBridge, KoinComponent {

    private val loginUserApiUc: DomainLayerContract.UseCase by inject("loginUserApiUc")
    private val registerUserApiUc: DomainLayerContract.UseCase by inject("registerUserApiUc")

    override fun <T> loginUser(params: List<T>, onResult: (Either<Failure, Boolean>) -> Unit) {
        loginUserApiUc.invoke(params = params, onResult = onResult)
    }

    override fun <T> registerUser(params: List<T>, onResult: (Either<Failure, Boolean>) -> Unit) {
        registerUserApiUc.invoke(params = params, onResult = onResult)
    }

}