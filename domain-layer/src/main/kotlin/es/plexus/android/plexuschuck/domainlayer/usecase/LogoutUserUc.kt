package es.plexus.android.plexuschuck.domainlayer.usecase

import arrow.core.Either
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo

const val LOGOUT_UC_TAG = "logoutUserUc"

internal class LogoutUserUc(
    private val authenticationRepository: DomainlayerContract.Datalayer.AuthenticationRepository<FailureBo, Boolean>
) : DomainlayerContract.Presentationlayer.UseCase<Any, Boolean> {

    override suspend fun run(params: Any?): Either<FailureBo, Boolean> = authenticationRepository.logoutUser()

}
