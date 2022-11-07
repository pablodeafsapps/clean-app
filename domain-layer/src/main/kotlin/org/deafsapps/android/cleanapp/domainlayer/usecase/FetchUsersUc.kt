package org.deafsapps.android.cleanapp.domainlayer.usecase

import arrow.core.Either
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.domain.FailureBo

const val FETCH_USERS_UC_TAG = "fetchUsersUc"

/**
 * A use-case which allows to fetch a list of jokes from a repository
 *
 * @property [dataRepository] The repository responsible of bringing the required data
 */
class FetchUsersUc(private val persistenceRepository: DomainlayerContract.Datalayer.PersistencyRepository<String>) :
    DomainlayerContract.Presentationlayer.UseCase<Any, String> {

    override suspend fun run(params: Any?): Either<FailureBo, String> = persistenceRepository.fetchUsers()

}
