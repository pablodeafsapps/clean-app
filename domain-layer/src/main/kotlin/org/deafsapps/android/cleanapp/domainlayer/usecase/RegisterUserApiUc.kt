package org.deafsapps.android.cleanapp.domainlayer.usecase

import org.deafsapps.android.cleanapp.datalayer.base.Failure
import org.deafsapps.android.cleanapp.domainlayer.DomainLayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class RegisterUserApiUc : DomainLayerContract.UseCase<String?>, KoinComponent {

    private val repository: DomainLayerContract.Repository<String?> by inject()

    override fun invoke(params: List<String?>?, onResult: (Either<Failure, Boolean>) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun run(params: List<String?>?): Either<Failure, Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}