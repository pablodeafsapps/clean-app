package es.plexus.android.plexuschuck.domainlayer.usecase

import arrow.core.Either
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo

const val SAVE_USERS_UC_TAG = "saveUsersUc"

/**
 * A use-case which allows to fetch a list of jokes from a repository
 *
 * @property [dataRepository] The repository responsible of bringing the required data
 */
class SaveUsersUc(private val persistenceRepository: DomainlayerContract.Datalayer.PersistencyRepository<String>) :
    DomainlayerContract.Presentationlayer.UseCase<Any, Boolean> {

    override suspend fun run(params: Any?): Either<FailureBo, Boolean> = persistenceRepository.saveUsers()

}
