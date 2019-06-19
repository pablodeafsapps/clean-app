package es.plexus.android.plexuschuck.domainlayer.usecase

import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract
import es.plexus.android.plexuschuck.domainlayer.DomainlayerContract.Datalayer.Companion.ICNDB_REPOSITORY_TAG
import es.plexus.android.plexuschuck.domainlayer.base.Either
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBo
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

const val MAIN_UC_TAG = "fetchJokesApiUc"

class FetchJokesApiUc : DomainlayerContract.Presentationlayer.UseCase<List<String>?, List<JokeBo>>, KoinComponent {

    private val icndbRepository: DomainlayerContract.Datalayer.IcndbRepository<List<String>?, List<JokeBo>>
            by inject(ICNDB_REPOSITORY_TAG)

    override suspend fun run(params: List<String>?): Either<FailureBo, List<JokeBo>> =
        icndbRepository.fetchJokes(params = params)

}