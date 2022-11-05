package org.deafsapps.android.cleanapp.datalayer.datasource

import arrow.core.Either
import org.deafsapps.android.cleanapp.datalayer.domain.dtoToBo
import org.deafsapps.android.cleanapp.datalayer.service.IcndbApiService
import org.deafsapps.android.cleanapp.datalayer.utils.safeCall
import org.deafsapps.android.cleanapp.domainlayer.domain.FailureBo
import org.deafsapps.android.cleanapp.domainlayer.domain.JokeBoWrapper
import retrofit2.Retrofit

/**
 * This interface represents the contract to be complied by an entity to fit in as the jokes provider
 */
interface JokesDataSource {

    companion object {
        const val JOKES_DATA_SOURCE_TAG = "jokesDataSource"
        const val JOKES_API_SERVICE_TAG = "jokesApiService"
        const val ICNDB_BASE_URL = "http://api.icndb.com"
    }

    /**
     * Fetches a joke list in a wrapper or an error otherwise
     */
    suspend fun fetchJokesResponse(): Either<FailureBo, JokeBoWrapper>

}

/**
 * This class complies with [JokesDataSource] so that it is in charge of providing any required
 * information regarding jokes
 */
class IcndbDataSource(private val retrofitBuilder: Retrofit) : JokesDataSource {

    override suspend fun fetchJokesResponse(): Either<FailureBo, JokeBoWrapper> =
        retrofitBuilder.create(IcndbApiService::class.java).getJokesAsync()
            .safeCall(transform = { it.dtoToBo() })

}
