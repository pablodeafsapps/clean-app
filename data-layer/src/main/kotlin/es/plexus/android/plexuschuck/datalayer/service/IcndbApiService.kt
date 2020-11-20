package es.plexus.android.plexuschuck.datalayer.service

import es.plexus.android.plexuschuck.datalayer.domain.JokeDtoWrapper
import retrofit2.Response
import retrofit2.http.POST

/**
 *
 */
interface IcndbApiService {

    /**
     *
     */
    @POST("jokes")
    suspend fun getJokesAsync(): Response<JokeDtoWrapper>

}
