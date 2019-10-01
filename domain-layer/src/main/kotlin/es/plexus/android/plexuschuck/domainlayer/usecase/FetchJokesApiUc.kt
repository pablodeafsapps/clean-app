package es.plexus.android.plexuschuck.domainlayer.usecase

import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.DATA_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

const val MAIN_UC_TAG = "fetchJokesApiUc"

class FetchJokesApiUc : DomainlayerContract.Presentationlayer.UseCase<List<String>?, List<JokeBo>>,
    KoinComponent {

    private val icndbRepository: DomainlayerContract.Datalayer.DataRepository<List<String>?, List<JokeBo>>
            by inject(named(name = DATA_REPOSITORY_TAG))

    override suspend fun run(params: List<String>?): Either<FailureBo, List<JokeBo>> =
        icndbRepository.fetchJokes(params = params)

}