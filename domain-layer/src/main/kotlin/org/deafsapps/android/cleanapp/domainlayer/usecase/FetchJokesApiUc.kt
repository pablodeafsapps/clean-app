package org.deafsapps.android.cleanapp.domainlayer.usecase

import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBo
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class FetchJokesApiUc : DomainlayerContract.Presentationlayer.UseCase<List<String>?, List<JokeBo>>, KoinComponent {

    private val icndbRepository: DomainlayerContract.Datalayer.IcndbRepository<List<String>?, List<JokeBo>>
            by inject(named("icndbRepository"))

    override suspend fun run(params: List<String>?): Either<FailureBo, List<JokeBo>> =
        icndbRepository.fetchJokes(params = params)

}