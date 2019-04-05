package org.deafsapps.android.cleanapp.domainlayer.usecase

import org.deafsapps.android.cleanapp.domainlayer.DomainLayerContract
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class RegisterUserApiUc : DomainLayerContract.UseCase, KoinComponent {

    private val repository: DomainLayerContract.Repository by inject()

}