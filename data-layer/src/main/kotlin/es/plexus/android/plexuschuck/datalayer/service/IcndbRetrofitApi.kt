package es.plexus.android.plexuschuck.datalayer.service

import es.plexus.android.plexuschuck.datalayer.domain.JokeDtoWrapper
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.PUT

interface IcndbRetrofitApi {

    @PUT("jokes")
    fun getJokesAsync(): Deferred<Response<JokeDtoWrapper>>

}