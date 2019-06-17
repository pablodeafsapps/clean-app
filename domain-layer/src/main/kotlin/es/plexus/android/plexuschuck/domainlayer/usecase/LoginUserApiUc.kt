package es.plexus.android.plexuschuck.domainlayer.usecase

import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.base.FailureBo
import es.plexus.android.plexuschuck.domainlayer.di.FIREBASE_REPOSITORY_TAG
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

const val LOGIN_UC_TAG = "loginUserApiUc"
private const val REQUIRED_DATA = 2

class LoginUserApiUc : DomainlayerContract.Presentationlayer.UseCase<List<String?>?, Boolean>, KoinComponent {

    private val firebaseRepository: DomainlayerContract.Datalayer.FirebaseRepository<List<String>, Boolean>?
            by inject(name = FIREBASE_REPOSITORY_TAG)

    override suspend fun run(params: List<String?>?): Either<FailureBo, Boolean> =
        params?.filterNotNull()?.let {
            if (it.size >= REQUIRED_DATA) {
                firebaseRepository?.loginUser(it)
            } else {
                Either.Left(FailureBo.InputParamsError(msg = "Both e-mail and password are required"))
            }
        } ?: run {
            Either.Left(FailureBo.Unknown)
        }

}