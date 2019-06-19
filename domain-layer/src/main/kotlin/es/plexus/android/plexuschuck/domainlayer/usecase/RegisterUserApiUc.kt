package es.plexus.android.plexuschuck.domainlayer.usecase

import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

const val REGISTER_UC_TAG = "registerUserApiUc"
private const val REQUIRED_DATA = 2

internal class RegisterUserApiUc : DomainlayerContract.Presentationlayer.UseCase<List<String?>?, Boolean>,
    KoinComponent {

    private val repository: DomainlayerContract.Datalayer.FirebaseRepository<List<String>, Boolean> by inject("firebaseRepository")

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