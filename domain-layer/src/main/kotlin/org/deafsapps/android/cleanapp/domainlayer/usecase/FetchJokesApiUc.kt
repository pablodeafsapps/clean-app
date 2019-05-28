package org.deafsapps.android.cleanapp.domainlayer.usecase

import org.deafsapps.android.cleanapp.domainlayer.DomainlayerContract
import org.deafsapps.android.cleanapp.domainlayer.base.Either
import org.deafsapps.android.cleanapp.domainlayer.base.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBo
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class FetchJokesApiUc : DomainlayerContract.Presentationlayer.UseCase<List<String>?, List<JokeBo>>, KoinComponent {

    private val icndbRepository: DomainlayerContract.Datalayer.IcndbRepository<List<String>?, List<JokeBo>> by inject("icndbRepository")

    override suspend fun run(params: List<String>?): Either<FailureBo, List<JokeBo>> =
        icndbRepository.fetchJokes(params = params)

}