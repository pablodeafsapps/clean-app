package org.deafsapps.android.cleanapp.domainlayer.feature

import org.deafsapps.android.cleanapp.datalayer.base.Failure
import org.deafsapps.android.cleanapp.domainlayer.base.DomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.base.Either

interface LoginDomainLayerBridge : DomainLayerBridge {

    fun loginUser(onResult: (Either<Failure, Boolean>) -> Unit)

    fun registerUser(onResult: (Either<Failure, Boolean>) -> Unit)

}