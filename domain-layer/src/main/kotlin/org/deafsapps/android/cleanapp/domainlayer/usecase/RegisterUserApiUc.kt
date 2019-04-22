package org.deafsapps.android.cleanapp.domainlayer.usecase

import org.deafsapps.android.cleanapp.datalayer.base.Failure
import org.deafsapps.android.cleanapp.domainlayer.DomainLayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class RegisterUserApiUc : DomainLayerContract.UseCase, KoinComponent {

    private val repository: DomainLayerContract.Repository by inject()

    override fun <Params> invoke(params: Params?, onResult: (Either<Failure, Boolean>) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun <Params> run(params: Params): Either<Failure, Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}