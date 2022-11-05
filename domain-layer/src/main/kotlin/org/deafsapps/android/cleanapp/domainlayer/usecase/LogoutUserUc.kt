package org.deafsapps.android.cleanapp.domainlayer.usecase

import arrow.core.Either
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.domain.FailureBo

const val LOGOUT_UC_TAG = "logoutUserUc"

internal class LogoutUserUc(
    private val authenticationRepository: DomainlayerContract.Datalayer.AuthenticationRepository<FailureBo, Boolean>
) : DomainlayerContract.Presentationlayer.UseCase<Any, Boolean> {

    override suspend fun run(params: Any?): Either<FailureBo, Boolean> = authenticationRepository.logoutUser()

}
