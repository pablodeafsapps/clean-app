package org.deafsapps.android.cleanapp.domainlayer.feature.main

import org.deafsapps.android.cleanapp.domainlayer.base.DomainLayerBridge
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo

interface MainDomainLayerBridge<in T, out S> : DomainLayerBridge.Presentation {

    fun fetchJokes(params: T, onResult: (Either<FailureBo, S>) -> Unit = {})

}