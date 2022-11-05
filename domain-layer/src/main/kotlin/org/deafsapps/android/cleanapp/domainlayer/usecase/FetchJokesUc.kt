package org.deafsapps.android.cleanapp.domainlayer.usecase

import arrow.core.Either
import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.domain.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBoWrapper

const val FETCH_JOKES_UC_TAG = "fetchJokesUc"

/**
 * A use-case which allows to fetch a list of jokes from a repository
 *
 * @property [dataRepository] The repository responsible of bringing the required data
 */
class FetchJokesUc(private val dataRepository: DomainlayerContract.Datalayer.DataRepository<JokeBoWrapper>) :
    DomainlayerContract.Presentationlayer.UseCase<Any, JokeBoWrapper> {

    override suspend fun run(params: Any?): Either<FailureBo, JokeBoWrapper> = dataRepository.fetchJokes()

}
