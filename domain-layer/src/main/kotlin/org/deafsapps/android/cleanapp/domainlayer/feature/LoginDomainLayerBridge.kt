package org.deafsapps.android.cleanapp.domainlayer.feature

import org.deafsapps.android.cleanapp.domainlayer.base.DomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo

interface LoginDomainLayerBridge<T> : DomainLayerBridge.Presentation {

    fun loginUser(params: List<T>, onResult: (Either<FailureBo, Boolean>) -> Unit = {})
    fun registerUser(params: List<T>, onResult: (Either<FailureBo, Boolean>) -> Unit = {})

}