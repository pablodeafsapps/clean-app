package org.deafsapps.android.cleanapp.domainlayer.usecase

import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

private const val REQUIRED_DATA = 2

class RegisterUserApiUc : DomainlayerContract.Presentationlayer.UseCase<List<String?>?, Boolean>, KoinComponent {

    private val repository: DomainlayerContract.Datalayer.FirebaseRepository<List<String>, Boolean> by inject(named("firebaseRepository"))

    override suspend fun run(params: List<String?>?): Either<FailureBo, Boolean> =
        params?.filterNotNull()?.let {
            if (it.size >= REQUIRED_DATA) {
                repository.registerUser(it)
            } else {
                Either.Left(FailureBo.Unknown)
            }
        } ?: run {
            Either.Left(FailureBo.Unknown)
        }

}