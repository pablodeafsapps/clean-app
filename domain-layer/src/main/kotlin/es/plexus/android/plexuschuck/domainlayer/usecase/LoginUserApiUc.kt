package es.plexus.android.plexuschuck.domainlayer.usecase

import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.AUTHENTICATION_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

const val LOGIN_UC_TAG = "loginUserApiUc"
private const val REQUIRED_DATA = 2

internal class LoginUserApiUc :
    DomainlayerContract.Presentationlayer.UseCase<List<String?>?, Boolean>,
    KoinComponent {

    private val firebaseRepository: DomainlayerContract.Datalayer.AuthenticationRepository<List<String>, Boolean>?
            by inject(named(name = AUTHENTICATION_REPOSITORY_TAG))

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