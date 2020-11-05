package es.plexus.android.plexuschuck.domainlayer.usecase

import arrow.core.Either
import arrow.core.left
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.domain.ErrorMessage
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.UserLoginBo

const val LOGIN_UC_TAG = "loginUserUc"

internal class LoginUserUc(private val authenticationRepository: DomainlayerContract.Datalayer.AuthenticationRepository<UserLoginBo, Boolean>) :
    DomainlayerContract.Presentationlayer.UseCase<UserLoginBo, Boolean> {

    override suspend fun run(params: UserLoginBo?): Either<FailureBo, Boolean> =
        params.takeIf { !it?.email.isNullOrEmpty() && !it?.password.isNullOrEmpty() }?.let {
            authenticationRepository.loginUser(it)
        } ?: run {
            FailureBo.InputParamsError(ErrorMessage.ERROR_PARAMS_CANNOT_BE_EMPTY).left()
        }

}