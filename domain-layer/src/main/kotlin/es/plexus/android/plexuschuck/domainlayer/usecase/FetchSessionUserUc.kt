package es.plexus.android.plexuschuck.domainlayer.usecase

import arrow.core.Either
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.UserSessionBo

const val FETCH_USER_SESSION_UC_TAG = "fetchSessionUserUc"

internal class FetchSessionUserUc(
    private val sessionRepository: DomainlayerContract.Datalayer.SessionRepository<UserSessionBo>
) : DomainlayerContract.Presentationlayer.UseCase<Any, UserSessionBo> {

    override suspend fun run(params: Any?): Either<FailureBo, UserSessionBo> =
        sessionRepository.sessionUser()

}
