package org.deafsapps.android.cleanapp.domainlayer.feature

import org.deafsapps.android.cleanapp.domainlayer.DomainLayerContract
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class LoginDomainLayerBridgeImpl : LoginDomainLayerBridge, KoinComponent {

    private val loginUserApiUc: DomainLayerContract.UseCase by inject("loginUserApiUc")
    private val registerUserApiUc: DomainLayerContract.UseCase by inject("registerUserApiUc")

    override fun loginUser() {
        loginUserApiUc
    }

    override fun registerUser() {
        registerUserApiUc
    }

}