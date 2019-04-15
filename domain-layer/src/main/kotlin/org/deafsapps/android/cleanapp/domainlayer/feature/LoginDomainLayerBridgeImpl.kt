package org.deafsapps.android.cleanapp.domainlayer.feature

import kotlinx.coroutines.coroutineScope
import org.deafsapps.android.cleanapp.domainlayer.DomainLayerContract
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlinx.coroutines.launch

class LoginDomainLayerBridgeImpl : LoginDomainLayerBridge, KoinComponent {

    private val loginUserApiUc: DomainLayerContract.UseCase by inject("loginUserApiUc")
    private val registerUserApiUc: DomainLayerContract.UseCase by inject("registerUserApiUc")

    override fun loginUser() {
        coroutineScope {
            loginUserApiUc.enqueue()
        }
    }

    override fun registerUser() {
        registerUserApiUc.enqueue()
    }

}