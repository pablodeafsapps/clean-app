package org.deafsapps.android.cleanapp.domainlayer.feature.login

import org.deafsapps.android.cleanapp.domainlayer.base.DomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo

interface LoginDomainLayerBridge<in T, out S> : DomainLayerBridge.Presentation {

    fun loginUser(params: T, onResult: (Either<FailureBo, S>) -> Unit = {})
    fun registerUser(params: T, onResult: (Either<FailureBo, S>) -> Unit = {})

}