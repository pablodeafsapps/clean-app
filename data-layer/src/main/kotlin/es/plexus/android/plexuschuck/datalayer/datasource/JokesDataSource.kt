package es.plexus.android.plexuschuck.datalayer.datasource

import arrow.core.Either
import es.plexus.android.plexuschuck.datalayer.domain.dtoToBo
import es.plexus.android.plexuschuck.datalayer.service.IcndbApiService
import es.plexus.android.plexuschuck.datalayer.utils.safeCall
import es.plexus.android.plexuschuck.domainlayer.domain.FailureBo
import es.plexus.android.plexuschuck.domainlayer.domain.JokeBoWrapper
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
