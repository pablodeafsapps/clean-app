package org.deafsapps.android.cleanapp.domainlayer.usecase

import arrow.core.Either
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.domain.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.UserSessionBo

const val FETCH_USER_SESSION_UC_TAG = "fetchSessionUserUc"

internal class FetchSessionUserUc(
    private val sessionRepository: DomainlayerContract.Datalayer.SessionRepository<UserSessionBo>
) : DomainlayerContract.Presentationlayer.UseCase<Any, UserSessionBo> {

    override suspend fun run(params: Any?): Either<FailureBo, UserSessionBo> =
        sessionRepository.sessionUser()

}
